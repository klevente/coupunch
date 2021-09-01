export function action(serviceCall, successText = null, errorText = null) {
    return {
        serviceCall,
        successText,
        errorText,
    };
}
