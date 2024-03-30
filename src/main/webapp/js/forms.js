$(function() {
   $('form.form-ajax').submit((e) => {
       e.preventDefault();

       const load = $("#loading");

       const form = $(e.target);

       load.fadeIn(200).css("display", "flex");
       $.post({
           url: form.attr("action"),
           dataType: "json",
           data: form.serialize(),
           success: function (response) {
               load.fadeOut(200);
               if (response.error === true) {
                   Dialog.response({
                       type: "warning",
                       message: response.message
                   })
                   load.fadeOut(200);
                   return;
               }

               if (typeof response.alert !== "undefined") {
                   Dialog.alert(response.alert);
               }

               if (typeof response.redirect !== "undefined") {
                   window.location.href = response.redirect;
               }
           },
           error: function (request, status, error) {
               Dialog.response({type: 'danger'});
               load.fadeOut();
           },
       });
   });
});

function setCampoEndereco()
{
    const cep = $("#txtCep");
    cep.mask("00000-000");
    cep.on("blur", function() {
        const load = $("#loading");

        $("#txtLogradouro").val("");
        $("#txtBairro").val("");
        $("#txtCidade").val("");
        $("#txtEstado").val("");

        // Obter o valor do CEP
        var cep = $(this).val();

        // Remover caracteres não numéricos do CEP
        cep = cep.replace(/\D/g, '');

        // Verificar se o CEP possui o formato correto
        if (cep.length === 8) {
            load.show();
            // Fazer solicitação AJAX para obter informações do CEP
            $.ajax({
                url: "https://viacep.com.br/ws/" + cep + "/json/",
                type: "GET",
                dataType: "json",
                success: function(data) {
                    load.hide();
                    if (typeof data.erro != "undefined") {
                        Dialog.alert({
                            message: `Dados dos CEP não encontrado!`,
                            type: "error"
                        });
                        return;
                    }
                    $("#txtLogradouro").val(data.logradouro);
                    $("#txtBairro").val(data.bairro);
                    $("#txtCidade").val(data.localidade);
                    $("#txtEstado").val(data.uf);
                },
                error: function() {
                    Dialog.alert({
                        message: `Erro ao buscar informações do CEP`,
                        type: "error"
                    });
                }
            });
        }
    });
}