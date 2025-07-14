export class Contact {
	id = null;
	title = "";
	description = "";
	contact = "";
	user = null;

	constructor(id, title, description, contact, user) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.contact = contact;
		this.user = user;
	}
}