function aplicarMascaraCpf(campo) {
    campo.addEventListener('input', function () {
        let valor = campo.value.replace(/\D/g, '');
        valor = valor.substring(0, 11);

        if (valor.length > 9) {
            valor = valor.replace(/(\d{3})(\d{3})(\d{3})(\d{1,2})/, '$1.$2.$3-$4');
        } else if (valor.length > 6) {
            valor = valor.replace(/(\d{3})(\d{3})(\d{1,3})/, '$1.$2.$3');
        } else if (valor.length > 3) {
            valor = valor.replace(/(\d{3})(\d{1,3})/, '$1.$2');
        }

        campo.value = valor;
    });

    const formulario = campo.closest('form');
    if (formulario) {
        formulario.addEventListener('submit', function () {
            campo.value = campo.value.replace(/\D/g, '');
        });
    }
}

function aplicarMascaraCnpj(campo) {
    campo.addEventListener('input', function () {
        let valor = campo.value.replace(/\D/g, '');
        valor = valor.substring(0, 14);

        if (valor.length > 12) {
            valor = valor.replace(/(\d{2})(\d{3})(\d{3})(\d{4})(\d{1,2})/, '$1.$2.$3/$4-$5');
        } else if (valor.length > 8) {
            valor = valor.replace(/(\d{2})(\d{3})(\d{3})(\d{1,4})/, '$1.$2.$3/$4');
        } else if (valor.length > 5) {
            valor = valor.replace(/(\d{2})(\d{3})(\d{1,3})/, '$1.$2.$3');
        } else if (valor.length > 2) {
            valor = valor.replace(/(\d{2})(\d{1,3})/, '$1.$2');
        }

        campo.value = valor;
    });

    const formulario = campo.closest('form');
    if (formulario) {
        formulario.addEventListener('submit', function () {
            campo.value = campo.value.replace(/\D/g, '');
        });
    }
}
