package com.steveperkins.config;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;
import com.bettercloud.vault.response.AuthResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootApplication
public class SpringConfigSampleAppApplication {

	public static void main(String[] args) throws VaultException {
		final String token = vaultToken();
		final List<String> argsList = Arrays.stream(args).collect(Collectors.toList());
		argsList.add("--spring.cloud.config.token=" + token);
		args = argsList.toArray(new String[argsList.size()]);

		SpringApplication.run(SpringConfigSampleAppApplication.class, args);
	}

	private static String vaultToken() throws VaultException {
		final String vaultUrl = Optional.ofNullable(System.getProperty("VAULT_URL"))
				.orElseThrow(() -> new RuntimeException("The system property \"VAULT_URL\" is not set"));
		final String vaultUsername = Optional.ofNullable(System.getProperty("VAULT_USERNAME"))
				.orElseThrow(() -> new RuntimeException("The system property \"VAULT_USERNAME\" is not set"));
		final String vaultPassword = Optional.ofNullable(System.getProperty("VAULT_PASSWORD"))
				.orElseThrow(() -> new RuntimeException("The system property \"VAULT_PASSWORD\" is not set"));

		final VaultConfig vaultConfig = new VaultConfig(vaultUrl);
		final Vault vault = new Vault(vaultConfig);
		final AuthResponse authResponse = vault.auth().loginByUserPass(vaultUsername, vaultPassword);
		return authResponse.getAuthClientToken();
	}

}
