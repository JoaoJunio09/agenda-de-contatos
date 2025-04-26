import { setPerson } from "../models/personModel.js";

fetch('http://localhost:8080/api/person/v1/10')
	.then(response => {
		console.log("Status da requisição:", response.status);
		return response.text();
	})
	.then(text => {
		try {
			const person = JSON.parse(text);
			console.log("Dados recebidos:", person);
			if (!person) {
				throw new Error("Dados inválidos");
			}
			else {
				setPerson(person);
				console.log("Dados armazenados:", person);

				document.dispatchEvent(new CustomEvent("dataPersonSuccess"));
			}
		}
		catch (error) {
			console.log("Erro ao converter para texto:", error);
		}
	})
	.catch(error => {
		console.log("Erro ao consumir a API:", error)
	})
