import { PersonService } from "../service/personService.js";
import { openMessageFailureRequest } from "../utils/messageFailureRequest.js";

const tableBody = document.querySelector("#table-body");
const textSearch = document.querySelector("#input-search-user");

const buttonSearch = document.querySelector("#button-search");

buttonSearch.addEventListener("click", () => {
	const search = textSearch.value;

	if (search == "" || search == undefined || search == null) {
		openMessageFailureRequest({ title: "Erro ao buscar usuário", body: "Informe um conteúdo." });
	}

	

	console.log(search);
});

async function findAllPerson() {
	try {
		const data = await PersonService.listar();
		console.log(data);
		
		tableBody.innerHTML = "";

		data.forEach(user => {
			const row = document.createElement("tr");

			row.innerHTML = `
				<tr>
					<td>${user.person.firstName}</td>
					<td>${user.email}</td>
					<td>${user.person.gender}</td>
					<td>${user.person.phone}</td>
					<td colspan="2">
						<div class="action-buttons">
							<img src="../assets/img/editar.png" alt="" id="update">
							<img src="../assets/img/cross.png" alt="" id="delete">
						</div>
					</td>
				</tr>
			`;

			tableBody.appendChild(row);
		});
	}
	catch (error) {
		console.log(error);
	}
}

findAllPerson();

