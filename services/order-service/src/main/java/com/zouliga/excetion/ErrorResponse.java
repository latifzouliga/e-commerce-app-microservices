package com.zouliga.excetion;

import java.util.Map;

public record ErrorResponse(Map<String, String> errors) {
}
