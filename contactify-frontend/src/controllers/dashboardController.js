import { UserService } from "../service/userService.js";
import { ContactService } from "../service/contactService.js";

const buttonChartUserRegister = document.querySelector("#user-register");
const buttonChartContactRegister = document.querySelector("#contact-register");

const ctxOne = document.querySelector("#chart-one").getContext("2d");
const ctxAgeRelationship = document.querySelector("#chart-two").getContext("2d");
const ctxUsersWhoAddedTheMostContacts = document.querySelector("#chart-three").getContext("2d");

document.addEventListener("DOMContentLoaded", async () => {

	const users = await UserService.findAll();
	const contacts = await ContactService.findAll();
	
	let countUsersActive = 0;

	users.forEach(user => {
		if (user.userStatus === "ACTIVE") {
			countUsersActive++;
		}
	});

	document.querySelector("#number-users").textContent = users.length;
	document.querySelector("#number-users-active").textContent = countUsersActive;
	document.querySelector("#number-contacts").textContent = contacts.length;

	const chartUserAllRegister = new Chart(ctxOne, {
		type: 'bar',
		data: {
			labels: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab'],
			datasets: [
				{
					label: 'Número de cadastros',
					data: [1, 0, 5, 4, 7, 0, 18],
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
					text: 'Cadastros diários',
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

	const chartUserAgeRelationship = new Chart(ctxAgeRelationship, {
		type: 'doughnut',
		data: {
			labels: ['Menor de 18', 'Maior de 18', 'Maior de 30'],
			datasets: [{
				label: 'Quantidade de usuários',
				data: [19, 7, 1],
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

	const chartUsersWhoAddedTheMostContacts = new Chart(ctxUsersWhoAddedTheMostContacts, {
		type: 'line',
		data: {
			labels: ['Acessos', 'Adicionados', 'Editados', 'Excluídos', 'Tempo médio'],
			datasets: [
				{
					label: 'Admin',
					data: [100, 90, 30, 5, 12],
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
					data: [40, 25, 5, 0, 8],
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
});

function chat() {
	const chartOne = new Chart(ctx, {
		type: 'line',
		data: {
			labels: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab'],
			datasets: [
				{
					label: 'Acessos',
					data: [10, 20, 30, 78, 12, 30, 49],
					backgroundColor: 'blue'
				},
				{
					label: 'Usuários cadastrados',
					data: [5, 15, 25, 20, 10, 90, 5],
					backgroundColor: 'green'
				}
			]
		},
		options: {
			responsive: true,
			scales: {
				y: {
					beginAtZero: true,
					ticks: {
						color: '#ccc',
						font: {
							size: 14,
							weight: 'bold'
						}
					},
					grid: {
						color: 'rgba(200, 200, 200, 0.2)'
					}
				},
				x: {
					ticks: {
						color: '#ccc',
						font: {
							size: 12
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
					text: 'Diário',
					font: {
						size: 24,
						weight: 'bold'
					},
					color: '#333'
				},
				legend: {
					display: true,
					position: 'bottom', // ou 'top', 'left', 'right'
					labels: {
						color: '#000',
						font: {
							size: 12
						}
					}
				}
			}
		},
		animation: {
			duration: 1000,
			easing: 'easeInOutBounce'
		}
	});
}