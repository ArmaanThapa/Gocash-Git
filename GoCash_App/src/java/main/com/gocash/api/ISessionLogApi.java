package com.gocash.api;

public interface ISessionLogApi {

	void logUserLoggedIn(Long id, String id2, String remoteAddr, String machineId, String simId);

}
