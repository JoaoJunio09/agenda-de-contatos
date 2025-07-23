const BASE_URL = "http://localhost:8080";

const API_BASE_URL_FINDALL                 = `${BASE_URL}/api/user/v1`;
const API_BASE_URL_FINDALLPAGEABLE         = `${BASE_URL}/api/user/v1/pageable?page=:page&size=:size&direction=:direction`;
const API_BASE_URL_FINDBYID                = `${BASE_URL}/api/user/v1/:id`;
const API_BASE_URL_DETAILS_USER            = `${BASE_URL}/api/user/v1/:id/details`;
const API_BASE_URL_UPDATE                  = `${BASE_URL}/api/user/v1`;
const API_BASE_URL_CREATE                  = `${BASE_URL}/api/user/v1`;
const API_BASE_URL_GETALLDAILYREGISTERS    = `${BASE_URL}/api/user/v1/getDailyRegisters`;
const API_BASE_URL_GETALLDAILYLOGINS       = `${BASE_URL}/api/user/v1/getDailyLogins`;
const API_BASE_URL_REGISTERDAILYLOGIN      = `${BASE_URL}/api/user/v1/registerDailyLogin`;
const API_BASE_URL_GETALLADDEDCONTACTS     = `${BASE_URL}/api/user/v1/getAddedContacts`;
const API_BASE_URL_REGISTERADDEDCONTACT    = `${BASE_URL}/api/user/v1/registerAddedContact`;

async function findAll() {
	const response = await fetch(API_BASE_URL_FINDALL, {
		method: 'GET',
	});

	if (!response.ok) {
		throw new Error("Não foi possível listar Usuários");
	}

	const data = await response.json();
	return data;
}

async function findAllPageable(page, size, direction) {
	let url;
	url = API_BASE_URL_FINDALLPAGEABLE.replace(":page", page);
	url = url.replace(":size", size);
	url = url.replace("direction", direction);

	const response = await fetch(url, {
		method: 'GET',
	});

	const data = await response.json();
	return data;
}

async function findById(id) {
	const url = API_BASE_URL_FINDBYID.replace(":id", id);

	const response = await fetch(url, {
		method: 'GET',
	});

	if (!response.ok) {
		throw new Error("Erro ao buscar usuário");
	}

	const data = await response.json();
	return data;
}

async function findUserDetails(id) {
	const url = API_BASE_URL_DETAILS_USER.replace(":id", id);

	const response = await fetch(url, {
		method: 'GET',
	});

	if (!response.ok) {
		throw new Error("Erro ao busar dados do usuário");
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
		const errorData = await response.json().catch(() => ({}));
		const errorMessage = errorData.message || `Erro ${response.status}`;

		const objectError = {
			title: errorData.title,
			body: errorMessage || "",
		};

		throw objectError;
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
		throw new Error("Erro ao atualizar usuário");
	}

	const data = await response.json();
	return data;
}

async function getAllDailyRegister() {
	const response = await fetch(API_BASE_URL_GETALLDAILYREGISTERS, {
		method: 'GET',
	});

	if (!response.ok) {
		throw new Error("Não foi possível obter os cadastros diários");
	}

	const data = await response.json();
	return data;
}

async function getAllDailyLogins() {
	const response = await fetch(API_BASE_URL_GETALLDAILYLOGINS, {
		method: 'GET',
	});

	if (!response.ok) {
		throw new Error("Não foi possível obter os cadastros diários");
	}

	const data = await response.json();
	return data;
}

async function registerDailyLogin(dailyLogin) {
	const response = await fetch(API_BASE_URL_REGISTERDAILYLOGIN, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
		},
		body: JSON.stringify(dailyLogin)
	});

	if (!response.ok) {
		throw new Error("Não foi possível efetuar o login diário.");
	}

	const data = await response.json();
	return data;
}

async function getAllAddedContacts() {
	const response = await fetch(API_BASE_URL_GETALLADDEDCONTACTS, {
		method: 'GET'
	});

	if (!response.ok) {
		throw new Error("Não foi possível obter os dados de contatos adicionados.");
	}

	const data = await response.json();
	return data;
}

async function registerAddedContact(addedContact) {
	const response = await fetch(API_BASE_URL_REGISTERADDEDCONTACT, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
		},
		body: JSON.stringify(addedContact)
	});

	if (!response.ok) {
		throw new Error("Não foi possível efetuar o registrar contato adicionado.");
	}

	const data = await response.json();
	return data;
}

export const UserService = {
	findAll,
	findAllPageable,
	findById,
	findUserDetails,
	create,
	update,
	getAllDailyRegister,
	getAllDailyLogins,
	registerDailyLogin,
	getAllAddedContacts,
	registerAddedContact
};