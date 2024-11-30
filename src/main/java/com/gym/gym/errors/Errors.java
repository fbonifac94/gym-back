package com.gym.gym.errors;

public enum Errors {
	EXERCISES_IDS_NOT_FOUND("No se encontraron los ejercicios con ids %s"),
	TEACHER_NOTFOUND_MSG_ERROR("No se encontró el profesor con id %s"),
	TEACHER_BY_USERID_NOTFOUND_MSG_ERROR("No se encontró el profesor para el userId %s"),
	ROUTINE_NOTFOUND_MSG_ERROR("No se encontró la rutina con id %s"),
	CUSTOMER_NOTFOUND_MSG_ERROR("No se encontró el cliente con id %s"),
	CUSTOMER_BY_USERID_NOTFOUND_MSG_ERROR("No se encontró el cliente para el userId %s"),
	ADMIN_NOTFOUND_MSG_ERROR("No se encontró el administrador con id %s"),
	PERSON_NOTFOUND_MSG_ERROR("No se encontró la ppersona con id %s"),
	USER_NOTFOUND_MSG_ERROR("No se encontró el usuario con id %s"),
	USER_NOTFOUND_EMAIL_MSG_ERROR("No se encontró el usuario con mail %s"),
	EXERCISETYPE_NOTFOUND_MSG_ERROR("No se encontró el tipo de ejercicio con id %s"),
	EXERCISE_NOTFOUND_MSG_ERROR("No se encontró el ejercicio con id %s");

	private String message;

	private Errors(String message) {
		this.message = message;
	}

	public String withParams(Object... params) {
		return String.format(this.message, params);
	}
}
