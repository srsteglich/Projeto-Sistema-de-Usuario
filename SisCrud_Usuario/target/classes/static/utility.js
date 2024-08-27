// Mostra alert e depois desaparece
document.addEventListener('DOMContentLoaded', function () {
    var alerta = document.getElementById('alertaMensagem');
    if (alerta) {
        alerta.style.opacity = '1';
        alerta.style.display = 'block';

        setTimeout(function () {
            alerta.style.opacity = '0';
            setTimeout(function () {
                alerta.style.display = 'none';
            }, 2000); // Aguarda a duração
        }, 2500); //  = 2,5 segundos
    }
    var btnFecharAlerta = document.querySelector('.btn-close');
    if (btnFecharAlerta) {
        btnFecharAlerta.addEventListener('click', function() {
            alerta.style.opacity = '0';
            setTimeout(function () {
                alerta.style.display = 'none';
            }, 2000);
        });
    }
});
// Amplilar alguns botões
document.querySelectorAll('.btn').forEach(function(btn) {
    btn.addEventListener('mouseover', function() {
        btn.classList.add('btn-ampliado');
    });
    btn.addEventListener('mouseout', function() {
        btn.classList.remove('btn-ampliado');
    });
});

// Modal, avisar para excluir
function abrirModal(element) {
    var nome = element.getAttribute("data-nome");
    var id = element.getAttribute("data-id");

    // Atualiza o nome do usuário no modal
    document.getElementById("usuarioNome").textContent = nome;

    // Atualiza o link de exclusão com o ID correto
    document.getElementById("confirmDeleteBtn").setAttribute("href", "/usuario/admin/apagar/" + id);

    // Exibe o modal
    var myModal = new bootstrap.Modal(document.getElementById('deleteModal'));
    myModal.show();
}



