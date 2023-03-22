package com.gammax.ci.gammax.testbase;

import java.util.List;

import org.aeonbits.owner.Config;

@Config.Sources(value="file:${user.dir}/src/main/resources/data.properties")
public interface data extends Config{

	@Key("url")
	String url();
	
	@Key("SecretRecoveryPhrase")
	List<String> passwordRecoveryPhrase();
	
	@Key("MetamaskPassword")
	String metamaskpassword();
	
	@Key("dockerEnable")
	boolean dockerEnable();
}
