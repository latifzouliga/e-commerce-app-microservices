package com.zouliga.exception;

import java.util.Map;

public record ErrorResponse(Map<String, String> errors) {
}
