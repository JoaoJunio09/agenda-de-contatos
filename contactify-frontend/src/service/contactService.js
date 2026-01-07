const BASE_URL = "http://localhost:8080";

const API_BASE_URL_FINDALL  		    		  = `${BASE_URL}/api/contact/v1`;
const API_BASE_URL_FINDBYID 		    		  = `${BASE_URL}/api/contact/v1/:id`;
const API_BASE_URL_DELETE               		  = `${BASE_URL}/api/contact/v1/:id`;
const API_BASE_URL_FINDCONTACTSBYUSER   		  = `${BASE_URL}/api/contact/v1/findContactsByUser/:id`;
const API_BASE_URL_CREATE   		    		  = `${BASE_URL}/api/contact/v1`;
const API_BASE_URL_UPDATE   		     		  = `${BASE_URL}/api/contact/v1`;
const API_BASE_URL_GETALLEDITEDCONTACTS  		  = `${BASE_URL}/api/contact/v1/getEditedContacts`;
const API_BASE_URL_REGISTEREDITEDCONTACT  		  = `${BASE_URL}/api/contact/v1/registerEditedContact`;
const API_BASE_URL_GETALLDELETEDCONTACTS  		  = `${BASE_URL}/api/contact/v1/getDeletedContacts`;
const API_BASE_URL_REGISTERDELETEDCONTACT         = `${BASE_URL}/api/contact/v1/registerDeletedContact`;
const API_BASE_URL_GETALLDAILYCONTACTREGISTRATION = `${BASE_URL}/api/contact/v1/getDailyContactRegistration`;
const API_BASE_URL_FINDCONTACTSBYSEARCH 		  = `${BASE_URL}/experimental/findContactsBySearch`;

async function findAll() {
	const response = await fetch(API_BASE_URL_FINDALL, {
		method: 'GET'
	});

	const data = await response.json();
	return data;
}

async function findById(id) {
	const url = API_BASE_URL_FINDBYID.replace(":id", id);

	const response = await fetch(url, {
		method: 'GET'
	});

	if (!response.ok) {
		throw new Error("Não foi possível buscar contato");
	}

	const data = await response.json();
	return data;
}

async function findContactsByUser(id) {
	const url = API_BASE_URL_FINDCONTACTSBYUSER.replace(":id", id);

	const response = await fetch(url, {
		method: 'GET'
	});

	if (!response.ok) {
		throw new Error("Não foi possível buscar contatos");
	}

	const data = await response.json();
	return data;
}

async function create(user) {
	const response = await fetch(API_BASE_URL_CREATE, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
		},
		body: JSON.stringify(user)
	});

	if (!response.ok) {
		throw new Error("Erro ao registrar Contato");
	}

	const data = await response.json();
	return data;
}

async function update(user) {
	const response = await fetch(API_BASE_URL_UPDATE, {
		method: 'PUT',
		headers: {
			'Content-Type': 'application/json',
		},
		body: JSON.stringify(user)
	});

	if (!response.ok) {
		throw new Error("Erro ao atualizar Contato");
	}

	const data = await response.json();
	return data;
}

async function deleteContact(id) {
	const url = API_BASE_URL_DELETE.replace(":id", id);
	
	const response = await fetch(url, {
		method: 'DELETE'
	});

	if (!response.ok) {
		throw new Error({ type: "error", message: "Erro ao deletar contato." });
	}
	else {
		return { type: "success", message: "Contato deletado." };
	}
}

async function getAllEditedContacts() {
	const response = await fetch(API_BASE_URL_GETALLEDITEDCONTACTS, {
		method: 'GET'
	});

	if (!response.ok) {
		throw new Error("Não foi possível obter os dados dos contatos.");
	}

	const data = await response.json();
	return data;
}

async function registerEditedContact(editedContact) {
	const response = await fetch(API_BASE_URL_REGISTEREDITEDCONTACT, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(editedContact)
	});

	if (!response.ok) {
		throw new Error("Não foi possível adicionar o contato editado.");
	}

	const data = response.json();
	return data;
}

async function getAllDeletedContacts() {
	const response = await fetch(API_BASE_URL_GETALLDELETEDCONTACTS, {
		method: 'GET'
	});

	if (!response.ok) {
		throw new Error("Não foi possível obter os dados dos contatos.");
	}

	const data = await response.json();
	return data;
}

async function registerDeletedContact(deletedContact) {
	const response = await fetch(API_BASE_URL_REGISTERDELETEDCONTACT, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(deletedContact)
	});

	if (!response.ok) {
		throw new Error("Não foi possível adicionar o contato editado.");
	}

	const data = response.json();
	return data;
}

async function getAllDailyContactRegistration() {
	const response = await fetch(API_BASE_URL_GETALLDAILYCONTACTREGISTRATION, {
		method: 'GET',
		headers: {
			'Content-Type': "application/xml"
		}
	});

	if (!response.ok) {
		throw new Error("Não foi possível obter os dados dos contatos.");
	}

	const data = await response.json();
	return data;
}

async function findContactsBySearch(search) {
	const response = await fetch(API_BASE_URL_FINDCONTACTSBYSEARCH, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(search)
	});

	if (!response.ok) {
		throw new Error("Contato não encontrado");
	}

	const data = await response.json();
	return data;
}

export const ContactService = {
	findAll,
	findById,
	findContactsByUser,
	create,
	update,
	deleteContact,
	getAllEditedContacts,
	registerEditedContact,
	getAllDeletedContacts,
	registerDeletedContact,
	getAllDailyContactRegistration,
	findContactsBySearch
};