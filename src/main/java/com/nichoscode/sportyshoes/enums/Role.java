package com.nichoscode.sportyshoes.enums;

public enum Role {
	ADMIN,
	USER;
	
	@Override
    public String toString() {
        return "ROLE_" + name();
    }
}
