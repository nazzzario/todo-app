package com.nkrasnovoronka.todoapp.exception;

import java.util.Date;

public record ErrorMessage(int value, Date date, String message, String description) {
}
