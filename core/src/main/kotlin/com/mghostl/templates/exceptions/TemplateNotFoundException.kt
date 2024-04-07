package com.mghostl.templates.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class TemplateNotFoundException(msg: String) : ResponseStatusException(HttpStatus.NOT_FOUND, msg)