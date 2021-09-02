import { createForm as felteCreateForm } from 'felte';
import { validator } from '@felte/validator-yup';
import { generate } from './array';

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
        extend: [validator],
        validateSchema: schema,
        onSubmit,
        onError,
        ...rest
    });
}

export function toIndexArray(array) {
    return generate(array.length, i => i);
}
