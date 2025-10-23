// Prevent multiple event listener attachments
let isInitialized = false;

let initialized = false;

document.addEventListener('DOMContentLoaded', () => {
    if (initialized) return;
    initialized = true;

    // Cache DOM elements
    const facultyForm = document.getElementById('form-faculty');
    const studentForm = document.getElementById('form-student');
    const assignmentForm = document.getElementById('form-assignment');
    const submissionForm = document.getElementById('form-submission');
    const facultyList = document.getElementById('faculty-list');
    const studentList = document.getElementById('student-list');
    const assignmentList = document.getElementById('assignment-list');
    const submissionList = document.getElementById('submission-list');
    const assignmentFacultySelect = document.getElementById('assignment-faculty');
    const submissionStudentSelect = document.getElementById('submission-student');
    const submissionAssignmentSelect = document.getElementById('submission-assignment');

    // Utility functions
    const showMessage = (message, isError = false) => {
        const messageDiv = document.getElementById('message-banner');
        messageDiv.className = `message show ${isError ? 'error' : 'success'}`;
        messageDiv.textContent = message;
        setTimeout(() => messageDiv.className = 'message', 3000);
    };

    const handleError = (error) => {
        console.error('Error:', error);
        showMessage(error.message || 'An error occurred', true);
    };

    // API calls
    const api = {
        async createFaculty(data) {
            const response = await fetch('/api/faculty', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(data)
            });
            if (!response.ok) throw new Error('Failed to create faculty');
            return response.json();
        },

        async createStudent(data) {
            const response = await fetch('/api/students', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(data)
            });
            if (!response.ok) throw new Error('Failed to create student');
            return response.json();
        },

        async createAssignment(data) {
            const response = await fetch('/api/assignments', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(data)
            });
            if (!response.ok) throw new Error('Failed to create assignment');
            return response.json();
        },

        async createSubmission(data) {
            const response = await fetch('/api/submissions', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(data)
            });
            if (!response.ok) throw new Error('Failed to create submission');
            return response.json();
        },

        async getFaculty() {
            const response = await fetch('/api/faculty');
            if (!response.ok) throw new Error('Failed to fetch faculty');
            return response.json();
        },

        async getStudents() {
            const response = await fetch('/api/students');
            if (!response.ok) throw new Error('Failed to fetch students');
            return response.json();
        },

        async getAssignments() {
            const response = await fetch('/api/assignments');
            if (!response.ok) throw new Error('Failed to fetch assignments');
            return response.json();
        },

        async getSubmissions() {
            const response = await fetch('/api/submissions');
            if (!response.ok) throw new Error('Failed to fetch submissions');
            return response.json();
        }
    };

    // Dropdown population functions
    const populateFacultyDropdown = async () => {
        try {
            const faculty = await api.getFaculty();
            assignmentFacultySelect.innerHTML = `
                <option value="">Select Faculty</option>
                ${faculty.map(f => `<option value="${f.id}">${f.name} - ${f.department}</option>`).join('')}
            `;
        } catch (error) {
            handleError(error);
        }
    };

    const populateStudentDropdown = async () => {
        try {
            const students = await api.getStudents();
            if (!submissionStudentSelect) {
                console.warn('submissionStudentSelect not found in DOM');
                return;
            }
            console.debug('populateStudentDropdown: fetched', students.length, 'students');
            submissionStudentSelect.innerHTML = `
                <option value="">Select Student</option>
                ${students.map(s => `<option value="${s.id}">${s.name} - ${s.department}</option>`).join('')}
            `;
        } catch (error) {
            handleError(error);
        }
    };

    const populateAssignmentDropdown = async () => {
        try {
            const assignments = await api.getAssignments();
            submissionAssignmentSelect.innerHTML = `
                <option value="">Select Assignment</option>
                ${assignments.map(a => `<option value="${a.id}">${a.title} (Due: ${a.dueDate})</option>`).join('')}
            `;
        } catch (error) {
            handleError(error);
        }
    };

    // UI Rendering functions
    const renderFacultyList = async () => {
        try {
            // Clear existing list first
            facultyList.innerHTML = '';
            const faculty = await api.getFaculty();
            // Create a Set to track unique IDs
            const seenIds = new Set();
            facultyList.innerHTML = faculty
                .filter(f => {
                    if (seenIds.has(f.id)) return false;
                    seenIds.add(f.id);
                    return true;
                })
                .map(f => `
                    <div class="p-4 bg-slate-50 rounded-lg border border-slate-200 hover:shadow-md transition-shadow">
                        <h3 class="font-bold text-indigo-700">${f.name}</h3>
                        <p class="text-sm text-gray-600">${f.email}</p>
                        <p class="text-xs text-gray-500 mt-1">${f.department}</p>
                    </div>
                `).join('');
            // Update faculty dropdown after new faculty is added
            await populateFacultyDropdown();
        } catch (error) {
            handleError(error);
        }
    };

    const renderStudentList = async () => {
        try {
            const students = await api.getStudents();
            studentList.innerHTML = students.map(s => `
                <div class="p-4 bg-slate-50 rounded-lg border border-slate-200 hover:shadow-md transition-shadow">
                    <h3 class="font-bold text-indigo-700">${s.name}</h3>
                    <p class="text-sm text-gray-600">${s.email}</p>
                    <p class="text-xs text-gray-500 mt-1">${s.department}</p>
                </div>
            `).join('');
        } catch (error) {
            handleError(error);
        }
    };

    const renderAssignmentList = async () => {
        try {
            const assignments = await api.getAssignments();
            assignmentList.innerHTML = assignments.map(a => `
                <div class="p-4 bg-white rounded-lg border border-gray-200 hover:shadow-md transition-shadow">
                    <div class="flex justify-between items-start">
                        <h3 class="font-bold text-lg text-gray-800">${a.title}</h3>
                        <span class="text-sm font-medium text-white bg-indigo-500 px-2 py-1 rounded-full">${new Date(a.dueDate).toLocaleDateString()}</span>
                    </div>
                    <p class="text-gray-600 mt-2">${a.description}</p>
                </div>
            `).join('');
        } catch (error) {
            handleError(error);
        }
    };

    const renderSubmissionList = async () => {
        try {
            const submissions = await api.getSubmissions();
            const assignments = await api.getAssignments();
            const students = await api.getStudents();

            // Sort submissions by latest first  
            submissions.sort((a, b) => new Date(b.submittedAt) - new Date(a.submittedAt));
            
            submissionList.innerHTML = submissions.map(s => {
                const assignmentId = s.assignment ? s.assignment.id : null;
                const studentId = s.student ? s.student.id : null;
                const assignment = assignmentId ? assignments.find(a => a.id == assignmentId) : null;
                const student = studentId ? students.find(st => st.id == studentId) : null;
                
                const statusClasses = {
                    'SUBMITTED': 'bg-blue-100 text-blue-800',
                    'IN_REVIEW': 'bg-yellow-100 text-yellow-800',
                    'APPROVED': 'bg-green-100 text-green-800',
                    'REJECTED': 'bg-red-100 text-red-800',
                };
                const statusClass = statusClasses[s.status] || 'bg-gray-100 text-gray-800';

                return `
                <div class="p-4 bg-white rounded-lg border border-gray-200 hover:shadow-md transition-shadow">
                    <div class="flex justify-between items-start">
                        <div>
                            <h3 class="font-bold text-lg text-gray-800">${assignment ? assignment.title : 'Unknown Assignment'}</h3>
                            <p class="text-sm text-gray-600">Submitted by: <span class="font-medium">${student ? student.name : 'Unknown Student'}</span></p>
                        </div>
                        <span class="text-xs font-medium px-2.5 py-0.5 rounded-full ${statusClass}">${s.status || 'N/A'}</span>
                    </div>
                    <div class="mt-3 flex justify-between items-center">
                        <p class="text-xs text-gray-500">Submitted on: ${new Date(s.submittedAt).toLocaleString()}</p>
                        ${s.fileUrl ? `<a href="${s.fileUrl}" class="text-sm text-indigo-600 hover:underline font-semibold" target="_blank">View Submission &rarr;</a>` : ''}
                    </div>
                </div>
            `}).join('');
        } catch (error) {
            handleError(error);
        }
    };

    // Event Listeners
    facultyForm?.addEventListener('submit', async (e) => {
        e.preventDefault();
        try {
            const formData = new FormData(e.target);
            const data = {
                name: formData.get('name'),
                email: formData.get('email'),
                department: formData.get('department')
            };
            await api.createFaculty(data);
            e.target.reset();
            showMessage('Faculty created successfully');
            await renderFacultyList();
        } catch (error) {
            handleError(error);
        }
    });

    studentForm?.addEventListener('submit', async (e) => {
        e.preventDefault();
        try {
            const formData = new FormData(e.target);
            const data = {
                name: formData.get('name'),
                email: formData.get('email'),
                department: formData.get('department')
            };
            await api.createStudent(data);
            e.target.reset();
            showMessage('Student created successfully');
            await renderStudentList();
            // Also refresh the submission student dropdown so newly created students appear
            await populateStudentDropdown();
        } catch (error) {
            handleError(error);
        }
    });

    assignmentForm?.addEventListener('submit', async (e) => {
        e.preventDefault();
        try {
            const formData = new FormData(e.target);
            const facultyId = formData.get('facultyId');
            
            if (!facultyId) {
                throw new Error('Please select a faculty member');
            }

            const data = {
                title: formData.get('title'),
                description: formData.get('description'),
                dueDate: formData.get('dueDate'),
                faculty: { id: parseInt(facultyId) }
            };
            await api.createAssignment(data);
            e.target.reset();
            showMessage('Assignment created successfully');
            await renderAssignmentList();
            // Update assignment dropdown for submissions after adding new assignment
            await populateAssignmentDropdown();
        } catch (error) {
            handleError(error);
        }
    });

    submissionForm?.addEventListener('submit', async (e) => {
        e.preventDefault();
        try {
            const formData = new FormData(e.target);
            const studentId = formData.get('studentId');
            const assignmentId = formData.get('assignmentId');
            
            if (!studentId || !assignmentId) {
                throw new Error('Please select both student and assignment');
            }

            const data = {
                student: { id: parseInt(studentId) },
                assignment: { id: parseInt(assignmentId) },
                fileUrl: formData.get('fileUrl') || ''
            };
            
            await api.createSubmission(data);
            e.target.reset();
            showMessage('Submission created successfully');
            await renderSubmissionList();
        } catch (error) {
            handleError(error);
        }
    });

    // Clear any existing data
    facultyList.innerHTML = '';
    studentList.innerHTML = '';
    assignmentList.innerHTML = '';
    submissionList.innerHTML = '';

    // Initial loading of data
    renderFacultyList();
    renderStudentList();
    renderAssignmentList();
    renderSubmissionList();

    // Initial population of dropdowns
    populateFacultyDropdown();
    populateStudentDropdown();
    populateAssignmentDropdown();
});