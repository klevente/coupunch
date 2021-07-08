export function action(serviceCall, successText = '', errorText = '') {
    return {
        serviceCall,
        successText,
        errorText,
    };
}
