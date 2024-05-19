package ua.foxminded.schooljdbc.models;

public class Enrollment {
	private int enrollmentId;
	private int studentId;
	private int courseId;
	
	public Enrollment(int enrollmentId, int studentId, int courseId) {
		this.enrollmentId = enrollmentId;
		this.studentId = studentId;
		this.courseId = courseId;
	}

	@Override
	public String toString() {
		return "Enrollment [EnrollmentId=" + enrollmentId + ", studentId=" + studentId + ", courseId=" + courseId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + enrollmentId;
		result = prime * result + courseId;
		result = prime * result + studentId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Enrollment other = (Enrollment) obj;
		if (enrollmentId != other.enrollmentId) {
			return false;
		}
		if (courseId != other.courseId) {
			return false;
		}
		if (studentId != other.studentId) {
			return false;
		}
		return true;
	}

	public int getEnrollmentId() {
		return enrollmentId;
	}

	public void setEnrollmentId(int enrollmentId) {
		this.enrollmentId = enrollmentId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
}

