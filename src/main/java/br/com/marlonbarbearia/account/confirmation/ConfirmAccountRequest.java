package br.com.marlonbarbearia.account.confirmation;

public record ConfirmAccountRequest(long accountId, String token) {
}
