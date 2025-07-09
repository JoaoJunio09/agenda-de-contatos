import { Contact } from "../models/contactModel.js";
import { UserService } from "../service/userService.js";
import { ContactService } from "../service/contactService.js";
import { showDetailsFailure, closeShowDetailsFailure } from "../utils/detailsFailure.js";
import { showDetailsSuccess } from "../utils/detailsSuccess.js";
import { openMessageFailureRequest } from "../utils/requestAndOthersfailure.js";
import { openMessageConfirmation } from "../utils/messageConfirmation.js";

let update = { trueOrFalse: false, contactId : null };

document.querySelector(".section-contacts").addEventListener('click', async (event) => {
	const buttonEditContact = event.target.closest(".button-edit-contact");
	const container = event.target.closest(".box-contact");

	if (buttonEditContact) {
		fade.classList.add("show-fade");
		containerAddContact.classList.add("show-container-add-contact");
		
		const titleEl = container.querySelector(".title-contact");
		const descriptionEl = container.querySelector(".description-contact");
		const contactEl = container.querySelector(".contact-contact");

		const title = titleEl?.textContent.trim();
		const description = descriptionEl?.textContent.trim();
		const contact = contactEl?.textContent.trim();

		document.querySelector("#title").value = title;
		document.querySelector("#description").value = description;
		document.querySelector("#contact").value = contact;

		update = { trueOrFalse: true, contactId: container.getAttribute("contact-id") };
	}

	const buttonDeleteContact = event.target.closest(".button-delete-contact");
	if (buttonDeleteContact) {
		const containerMsg = document.querySelector(".message-confirmation");
		containerMsg.classList.add("show-message-confirmation");

		const buttonCancel = document.querySelector(".buttonCancel");
		const buttonConfirmation = document.querySelector(".buttonConfirmation");
		const buttonClose = document.querySelector(".img-button-cancel");

		buttonCancel.addEventListener('click', () => {
			containerMsg.classList.remove("show-message-confirmation");
		});

		buttonClose.addEventListener('click', () => {
			containerMsg.classList.remove("show-message-confirmation");
		});

		buttonConfirmation.addEventListener('click', async () => {
			containerMsg.classList.remove("show-message-confirmation");
			
			try {
				const id = container.getAttribute("contact-id");
				
				if (id === undefined || id === null || id < 1) {
					throw new Error("Impossível deletar contato");
				}

				const data = await ContactService.deleteContact(id);

				if (data.type === "error") {
					openMessageFailureRequest({ title: "Erro inesperado", body: data.message });
				}
				else {
					const contacts = await findContactsByUser(userId);

					if (contacts != null && contacts != undefined) {
						fillInContacts(contacts);
					}
					
					showDetailsSuccess(data.message);

					if (contacts != null && contacts != undefined && contacts.length > 0) {
						fillInContacts(contacts);
					}
					else {
						openMessageFailureRequest({ title: "Não foi possível listar contatos", body: "Você não possui contatos registrados." });
						
						const container = document.querySelector(".section-contacts");
						const info = document.createElement("p");

						setTimeout(() => {
							info.textContent = "Você não possui contatos registrados.";
							info.style.color = "red";
							info.style.fontSize = "1rem";
							container.appendChild(info);
						}, 4500);
					}
				}
			}
			catch (error) {
				console.log("Inespected Error : " + error.message);
			}
		});
	}
});

let userId;

document.addEventListener('DOMContentLoaded', async () => {

	userId = localStorage.getItem("userId");

	const userLoginData = await UserService.findUserDetails(userId);

	document.querySelector(".info-account-data h2").textContent = userLoginData.person.firstName;
	document.querySelector(".info-account-data p").textContent = userLoginData.email;

	const menu_item_gerenciar_usuarios = document.querySelector(".menu-item-gerenciar-usuarios");

	if (localStorage.getItem('userAdmin') === "true") {
		menu_item_gerenciar_usuarios.style.display = "inherit";
	}
	else {
		menu_item_gerenciar_usuarios.style.display = "none";
	}

	const container = document.querySelector(".section-contacts");
	const info = document.createElement("p");

	const contacts = await findContactsByUser(userId);

	if (contacts != null && contacts != undefined && contacts.length > 0) {
		fillInContacts(contacts);
	}
	else {
		openMessageFailureRequest({ title: "Não foi possível listar contatos", body: "Você não possui contatos registrados." });
		
		setTimeout(() => {
			info.textContent = "Você não possui contatos registrados.";
			info.style.color = "red";
			info.style.fontSize = "1rem";
			container.appendChild(info);
		}, 6500);
	}
});

const buttonAddContact = document.getElementById("btn-add-contact");
const buttonCancelContact = document.getElementById("btn-cancel-contact");
const buttonsOpenModalAddContact = document.querySelectorAll("#btn-open-modal-add-contact");
const buttonCloseModalAddContact = document.getElementById("btn-close-modal-add-contact");

const fade = document.querySelector("#fade");
const containerAddContact = document.querySelector(".container-add-contact");

buttonsOpenModalAddContact.forEach(btn => {
	btn.addEventListener('click', () => {
		fade.classList.add("show-fade");
		containerAddContact.classList.add("show-container-add-contact");

		userId = localStorage.getItem("userId");
	});
});

function closeModalAddContact() {
	fade.classList.remove("show-fade");
	containerAddContact.classList.remove("show-container-add-contact");

	document.getElementById("title").value = "";
	document.getElementById("description").value = "";
	document.getElementById("contact").value = "";

	update = { trueOrFalse: false, contactId: null };
}

buttonCancelContact.addEventListener('click', closeModalAddContact);
buttonCloseModalAddContact.addEventListener('click', closeModalAddContact);

buttonAddContact.addEventListener('click', () => {
	if (update.trueOrFalse === true) {
		createOrUpdate(1);
	}
	else {
		createOrUpdate(0);
	}
});

async function createOrUpdate(code) {
	const titleContent = document.getElementById("title").value;
	const descriptionContent = document.getElementById("description").value;
	const contactContent = document.getElementById("contact").value;

	try {
		let obj;

		console.log(userId);
		const user = await findByIdUser(userId);

		const userLogin = {
			id: user.id,
			email: user.email,
			person: {
				id: user.person.id,
				firstName: user.person.firstName,
				lastName: user.person.lastName,
				email: user.person.email,
				birthDate: user.person.birthDate,
				gender: user.person.gender,
				nationality: user.person.nationality,
				phone: user.person.phone
			}
		};

		obj = {
			id: null,
			title: titleContent,
			description: descriptionContent,
			contact: contactContent,
			user: userLogin
		};

		validationData(titleContent, descriptionContent, contactContent, userLogin);

		console.log(obj);
		let data = { type: null, data: null };

		if (code == 0) {
			data =  { type: "create", data: await ContactService.create(obj) };
		}
		else {
			obj.id = Number.parseInt(update.contactId);

			data = { type: "update", data: await ContactService.update(obj) };
		}
		
		if (data != null && data != undefined) {
			closeShowDetailsFailure();

			if (data.type === "create") showDetailsSuccess("Registrado com sucesso.");
			else showDetailsSuccess("Atualizado com sucesso.");

			closeModalAddContact();

			const contacts = await findContactsByUser(userId);

			if (contacts != null && contacts != undefined) {
				fillInContacts(contacts);
			}
		}
	}
	catch (error) {
		showDetailsFailure(error.message);
	}
}

async function findByIdUser(id) {
	try {
		const user = await UserService.findById(id);
		return user;
	}
	catch (error) {
		throw Error("Não foi possível buscar usuário no banco.");
	}
}

async function findContactsByUser(id) {
	try {
		const data = await ContactService.findContactsByUser(id);
		return data;
	}
	catch (error) {
		openMessageFailureRequest({ title: "Erro inesperado", body: "Não foi possível listar os contatos." });
	}
}

async function fillInContacts(list) {
	try {
		const container = document.querySelector(".section-contacts");
		container.innerHTML = "";

		list.forEach(data => {
			const boxContact = document.createElement("div");
			boxContact.classList.add("box-contact");
			boxContact.setAttribute("contact-id", data.id);

			boxContact.innerHTML = `
				<div class="box-contact-header">
					<img src="../assets/img/homem (1).png" alt="">
					<div class="box-contact-header-info">
						<h1 class="title-contact">${data.title}</h1>
						<p class="contact-contact">${data.contact}</p>
					</div>
				</div>
				<div class="box-contact-body">
					<p class="description-contact">${data.description}</p>
				</div>
				<div class="box-contact-buttons">
					<button class="button-edit-contact">Editar</button>
					<button class="button-delete-contact" id="delete-btn">Excluir</button>
				</div>
			`;

			container.appendChild(boxContact);
		});
	}
	catch (error) {
		showDetailsFailure(error.message);
	}
}

function validationData(title, description, contact, user) {
	if (title === null || title === undefined || title === "") {
		throw new Error("Informe um título válido.");
	}

	if (description === null || description === undefined || description === "") {
		throw new Error("Preencha a descrição.");
	}

	if (contact === null || contact === undefined || undefined === "") {
		throw new Error("Informe um contato.");
	}

	if (user === null || user === undefined || user === "") {
		throw new Error("Usuário não encontrado.");
	}
}