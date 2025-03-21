# Frontend README

TODO: form registro:

Cuando el usuario selecciona una escuela en el dropdown:

Realiza una llamada a un endpoint del backend (podrías crear un nuevo controlador/servicio para esto) que devuelva la lista de school_specific_attributes para esa school_id.

Basándote en esta lista, genera dinámicamente los campos adicionales en el formulario. Por ejemplo, si para la Técnica 1 el backend devuelve [{ attribute_name: "Año del Hijo", data_type: "INT", is_required: true }, { attribute_name: "Especialidad del Hijo", data_type: "VARCHAR", is_required: false }], tu formulario mostrará estos dos campos.

Cuando el formulario se envíe, incluye tanto los datos básicos del miembro como un objeto con los valores de estos atributos dinámicos.
