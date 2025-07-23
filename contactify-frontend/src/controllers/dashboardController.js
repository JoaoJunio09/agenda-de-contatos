import { UserService } from "../service/userService.js";
import { ContactService } from "../service/contactService.js";
import { showDetailsFailure, showPasswordInvalid } from "../utils/detailsFailure.js";

const buttonChartUserRegister = document.querySelector("#user-register");
const buttonChartContactRegister = document.querySelector("#contact-register");

const ctxUserAllRegister = document.querySelector("#chart-one").getContext("2d");
const ctxAgeRelationship = document.querySelector("#chart-two").getContext("2d");
const ctxUsersWhoAddedTheMostContacts = document.querySelector("#chart-three").getContext("2d");

document.addEventListener("DOMContentLoaded", async () => {

	buttonChartUserRegister.addEventListener('click', () => {
		localStorage.setItem("codeToDifferentiateUserAndContactGraphics", 1);
		location.reload();
	});

	buttonChartContactRegister.addEventListener('click', () => {
		localStorage.setItem("codeToDifferentiateUserAndContactGraphics", 0);
		location.reload();
	});

	let countUsersActive = 0;
	const loading = document.querySelector(".container-loading");

	try {
		
		loading.style.display = "flex";

		const dailyContactsRegistrations = await ContactService.getAllDailyContactRegistration();
		const deletedContacts = await ContactService.getAllDeletedContacts();
		const editedContacts = await ContactService.getAllEditedContacts();
		const addedContacts = await UserService.getAllAddedContacts();
		const dailyLogins = await UserService.getAllDailyLogins();
		const users = await UserService.findAll();
		const contacts = await ContactService.findAll();
		const dailyRegisters = await UserService.getAllDailyRegister();

		users.forEach(user => {
			if (user.userStatus === "ACTIVE") {
				countUsersActive++;
			}
		});

		document.querySelector("#number-users").textContent = users.length;
		document.querySelector("#number-users-active").textContent = countUsersActive;
		document.querySelector("#number-contacts").textContent = contacts.length;

		const date = new Date();
		const dateFormat = date.toLocaleDateString();
		const dayOfToday = dateFormat.slice(0, 5);

		document.querySelector("#dayOfWeekAccess").textContent = dayOfToday;

		function fillAccessInDayOfWeek() {
			let accessesInTheSystemToday = 0;

			for (const access of dailyLogins) {
				const month = access.date.slice(5, 7);
				const day = access.date.slice(8, 10);
				const dayOfTodayInSystem = day + "/" + month;

				if (dayOfTodayInSystem === dayOfToday) {
					accessesInTheSystemToday++;
				}
			}
			document.querySelector("#number-access-day").textContent = accessesInTheSystemToday;
		}

		fillAccessInDayOfWeek();

		if (localStorage.getItem("codeToDifferentiateUserAndContactGraphics") === "1") {
			fillsInTheDailyRegistrationChart(collectsAndReturnsDailyRegistrationData(dailyRegisters));

			buttonChartUserRegister.style = `
				background-color: rgb(54, 1, 177);
				color: #fff;
			`;
		}
		else {
			fillsInTheDailyRegistrationChart(collectsAndReturnsDailyRegistrationData(dailyContactsRegistrations));

			buttonChartContactRegister.style = `
				background-color: rgb(54, 1, 177);
				color: #fff;
			`;
		}
		
		processesAndFillsInTheAgeRelationshipOfUsersChart(users);
		fillsInTheUsersWhoAddedTheMostContacts(dailyLogins, addedContacts, editedContacts, deletedContacts);
	} catch (error) {
		showDetailsFailure(error.message);
	} finally {
		loading.style.display = "none";
	}
});

function collectsAndReturnsDailyRegistrationData(list) {

	let numberOfDailyRegistrations = {
		sunday:    { dayOfWeek: "Dom", quantity: 0 },
		monday:    { dayOfWeek: "Seg", quantity: 0 },
		tuesday:   { dayOfWeek: "Ter", quantity: 0 },
		wednesday: { dayOfWeek: "Qua", quantity: 0 },
		thursday:  { dayOfWeek: "Qui", quantity: 0 },
		friday:    { dayOfWeek: "Sex", quantity: 0 },
		saturday:  { dayOfWeek: "Sab", quantity: 0 },
	}

	list.forEach(dailyRegister => {
		if (dailyRegister.day === 1) {
			numberOfDailyRegistrations.monday.quantity++;
		}
		if (dailyRegister.day === 2) {
			numberOfDailyRegistrations.tuesday.quantity++;
		}
		if (dailyRegister.day === 3) {
			numberOfDailyRegistrations.wednesday.quantity++;
		}
		if (dailyRegister.day === 4) {
			numberOfDailyRegistrations.thursday.quantity++;
		}
		if (dailyRegister.day === 5) {
			numberOfDailyRegistrations.friday.quantity++;
		}
		if (dailyRegister.day === 6) {
			numberOfDailyRegistrations.saturday.quantity++;
		}
		if (dailyRegister.day === 7) {
			numberOfDailyRegistrations.sunday.quantity++;
		}
	});

	return numberOfDailyRegistrations;
}

function fillsInTheDailyRegistrationChart(dailyRegisters) {

	if (dailyRegisters == null || dailyRegisters == undefined) {
		throw new Error("Não foi possível processar os dados.");
	}

	const chartUserAllRegister = new Chart(ctxUserAllRegister, {
		type: 'bar',
		data: {
			labels: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab'],
			datasets: [
				{
					label: "Número de cadastros",
					data: [
						dailyRegisters.sunday.quantity, 
						dailyRegisters.monday.quantity,
						dailyRegisters.tuesday.quantity, 
						dailyRegisters.wednesday.quantity, 
						dailyRegisters.thursday.quantity, 
						dailyRegisters.friday.quantity, 
						dailyRegisters.saturday.quantity
					],
					backgroundColor: '#3e00b0ff',
					borderRadius: 4
				}
			]
		},
		options: {
			responsive: true,
			scales: {
				y: {
					beginAtZero: true,
					ticks: {
						color: '#000000b7',
						font: {
							family: "Poppins",
							size: 14,
							weight: '400'
						}
					},
					grid: {
						color: 'rgba(200, 200, 200, 0.2)'
					}
				},
				x: {
					ticks: {
						color: '#000000b7',
						font: {
							family: "Poppins",
							size: 14,
							weight: '400'
						}
					}
				}
			},
			plugins: {
				tooltip: {
					enabled: true,
					backgroundColor: 'rgba(0,0,0,0.8)',
					titleColor: '#fff',
					bodyColor: '#fff', 
				},
				title: {
					display: true,
					text: "Cadastros diários",
					font: {
						size: 24,
						weight: 'bold'
					},
					color: '#333'
				},
				legend: {
					display: true,
					position: 'bottom',
					labels: {
						color: '#000',
						font: {
							size: 12
						}
					}
				}
			}
		}
	});

	if (localStorage.getItem("codeToDifferentiateUserAndContactGraphics") === "0" ) {
		chartUserAllRegister.options.plugins.title.text = "Registro Diário de Contatos";
	}
	else {
		chartUserAllRegister.options.plugins.title.text = "Cadastros diários";
	}
}

function processesAndFillsInTheAgeRelationshipOfUsersChart(users) {

	let underThe18 = 0;
	let overThe18  = 0;
	let overThe30  = 0;

	users.forEach(user => {
		const newDate = new Date();
		const date = user.person.birthDate;
		const dateSplit = date.split("-");
		const dateFormat = dateSplit[2].slice(0, 2) + "/" + dateSplit[1] + "/" + dateSplit[0];
		const diferencyAge = newDate.getFullYear() - dateFormat.slice(6, 10);

		if (diferencyAge < 16) {
			underThe18++;
		}
		if (diferencyAge > 16) {
			overThe18++;
		}
		if (diferencyAge > 30) {
			overThe30++;
		}
	});

	const chartUserAgeRelationship = new Chart(ctxAgeRelationship, {
		type: 'doughnut',
		data: {
			labels: ['Menor de 18', 'Maior de 18', 'Maior de 30'],
			datasets: [{
				label: 'Quantidade de usuários',
				data: [
					underThe18, 
					overThe18, 
					overThe30
				],
				backgroundColor: ['#f70000ff', '#00b000ff', '#1500d5ff'],
			}]
		},
		options: {
			responsive: true,
			scales: {
				y: {
					beginAtZero: true,
					ticks: {
						color: '#000000b7',
						font: {
							family: "Poppins",
							size: 14,
							weight: '400'
						}
					},
					grid: {
						color: 'rgba(200, 200, 200, 0.2)'
					}
				},
				x: {
					ticks: {
						color: '#000000b7',
						font: {
							family: "Poppins",
							size: 14,
							weight: '400'
						}
					}
				}
			},
			plugins: {
				tooltip: {
					enabled: true,
					backgroundColor: 'rgba(0,0,0,0.8)',
					titleColor: '#fff',
					bodyColor: '#fff', 
				},
				title: {
					display: true,
					text: 'Relação de idade',
					font: {
						size: 24,
						weight: 'bold'
					},
					color: '#333'
				},
				legend: {
					display: true,
					position: 'bottom',
					labels: {
						color: '#000',
						font: {
							size: 12
						}
					}
				}
			}
		}
	});
}

async function fillsInTheUsersWhoAddedTheMostContacts(dailyLogins, addedContacts, editedContacts, deletedContacts) {

	let dailyLoginsForAdmin = 0;
	let dailyLoginsForNotAdmin = 0;

	for (const login of dailyLogins) {
		const user_id = login.user_id;
		const user = await UserService.findUserDetails(user_id);

		if (user.userAdmin === "ALLOWED") {
			dailyLoginsForAdmin++;
		}
		if (user.userAdmin === "NOT_ALLOWED") {
			dailyLoginsForNotAdmin++;
		}
	};

	let addedContactsForAdmin = 0;
	let addedContactsForNotAdmin = 0;

	for (const addedContact of addedContacts) {
		const user_id = addedContact.user_id;
		const user = await UserService.findUserDetails(user_id);

		if (user.userAdmin === "ALLOWED") {
			addedContactsForAdmin++;
		}
		if (user.userAdmin === "NOT_ALLOWED") {
			addedContactsForNotAdmin++;
		}
	};

	let editedContactsForAdmin = 0;
	let editedContactsForNotAdmin = 0;

	for (const editedContact of editedContacts) {
		const user_id = editedContact.user_id;
		const user = await UserService.findUserDetails(user_id);

		if (user.userAdmin === "ALLOWED") {
			editedContactsForAdmin++;
		}
		if (user.userAdmin === "NOT_ALLOWED") {
			editedContactsForNotAdmin++;
		}
	};

	let deletedContactsForAdmin = 0;
	let deletedContactsForNotAdmin = 0;

	for (const deletedContact of deletedContacts) {
		const user_id = deletedContact.user_id;
		const user = await UserService.findUserDetails(user_id);

		if (user.userAdmin === "ALLOWED") {
			deletedContactsForAdmin++;
		}
		if (user.userAdmin === "NOT_ALLOWED") {
			deletedContactsForNotAdmin++;
		}
	};

	const chartUsersWhoAddedTheMostContacts = new Chart(ctxUsersWhoAddedTheMostContacts, {
		type: 'line',
		data: {
			labels: ['Acessos', 'Adicionados', 'Editados', 'Excluídos'],
			datasets: [
				{
					label: 'Administrador',
					data: [
						dailyLoginsForAdmin, 
						addedContactsForAdmin, 
						editedContactsForAdmin, 
						deletedContactsForAdmin
					],
					fill: false,
					borderColor: '#4f46e5',
					backgroundColor: '#4f46e5',
					tension: 0.3,
					pointStyle: 'circle',
					pointRadius: 5,
					pointHoverRadius: 7
				},
				{
					label: 'Usuário Comum',
					data: [
						dailyLoginsForNotAdmin, 
						addedContactsForNotAdmin, 
						editedContactsForNotAdmin, 
						deletedContactsForNotAdmin
					],
					fill: false,
					borderColor: '#22c55e',
					backgroundColor: '#22c55e',
					tension: 0.3,
					pointStyle: 'rectRot',
					pointRadius: 5,
					pointHoverRadius: 7
				}
			]
		},
		options: {
			responsive: true,
			plugins: {
			title: {
				display: true,
				text: 'Atividade dos Usuários',
				font: {
					size: 24,
					weight: 'bold'
				},
				color: '#333'
			},
			tooltip: {
				mode: 'index',
				intersect: false
			},
			legend: {
				position: 'bottom'
			}
			},
			interaction: {
				mode: 'nearest',
				axis: 'x',
				intersect: false
			},
			scales: {
				y: {
					beginAtZero: true,
					ticks: {
						stepSize: 20
					}
				}
			}
		}
	});
}