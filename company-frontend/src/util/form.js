import { createForm as felteCreateForm } from 'felte';
import reporter from '@felte/reporter-tippy';
import { validator } from '@felte/validator-yup';

export function isEditing(obj) {
    return obj && !!obj.id;
}

export function createForm({
                               schema,
                               onSubmit,
                               onError,
                               ...rest
                           }) {
    return felteCreateForm({
        extend: [validator, reporter()],
        validateSchema: schema,
        onSubmit,
        onError,
        ...rest
    });
}
