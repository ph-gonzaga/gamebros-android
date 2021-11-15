package br.senac.gamebros.model

data class ViaCep(
	val uf: String,
	val complemento: String,
	val ddd: String,
	val logradouro: String,
	val bairro: String,
	val localidade: String,
	val ibge: String,
	val siafi: String,
	val gia: String,
	val cep: String
)
