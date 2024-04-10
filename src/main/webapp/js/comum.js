const Dialog = {
    alert: function (obj) {
        if (obj.hasSmall && obj.hasSmall === true) {
            const Toast = Swal.mixin({
                toast: true,
                position: obj.position ?? "top-end",
                showConfirmButton: false,
                timer: 3000,
                timerProgressBar: true,
                didOpen: (toast) => {
                    toast.addEventListener("mouseenter", Swal.stopTimer);
                    toast.addEventListener("mouseleave", Swal.resumeTimer);
                },
            });

            Toast.fire({
                icon: obj.type ?? "info",
                title: obj.message ?? lang.went_wrong,
            }).then((result) => {
                if (typeof obj.redirect !== "undefined") {
                    window.location.href = obj.redirect;
                }
            });
        } else {
            Swal.fire({
                icon: obj.type ?? "info",
                title: obj.title ?? (obj.type == "success" ? "Uhull :)" : "Oops..."),
                html:
                    obj.message ??
                    `Não foi possivel finalizar o processo.`,
            }).then((result) => {
                if (typeof obj.redirect !== "undefined") {
                    window.location.href = obj.redirect;
                }
            });
        }
    },
    confirm: function (obj) {
        const alertConfirm = Swal.mixin({
            customClass: {
                confirmButton: "btn btn-warning mr-2",
                cancelButton: "btn btn-secondary",
            },
            buttonsStyling: false,
        });
        alertConfirm
            .fire({
                title: obj.title ?? "Oopa!",
                html: obj.message,
                icon: "warning",
                showCancelButton: true,
                confirmButtonText: `<b>Sim</b>`,
                cancelButtonText: "Não",
            })
            .then((result) => {
                if (result.isConfirmed) {
                    if (typeof obj.callback != 'undefined')
                        obj.callback();
                } else if (typeof obj.rollback != 'undefined') {
                    obj.rollback();
                }
            });
    },
    response: function (obj) {
        let type = "danger";
        let time = 5;
        let className = ".msg_response";
        let ajaxMessage = $(`<div class='alert alert-${type}'>Erro no processamento dos dados</div>`);

        if (typeof obj != "undefined") {
            if (typeof obj.class != "undefined")
                className = obj.class;
            if (typeof obj.type != "undefined")
                type = obj.type;
            if (typeof obj.time != "undefined")
                time = obj.time
            if (typeof obj.element != "undefined")
                ajaxMessage = $(obj.element);
            else if (typeof obj.message != "undefined")
                ajaxMessage = $(`<div class='alert alert-${type}'>${obj.message}</div>`);
            else if (typeof obj.messageArray != "undefined" && obj.messageArray.length > 0) {
                obj.messageArray.forEach((element, key) => {
                    let message = $(`<div class='alert alert-${type}'>${element}</div>`);
                    message.append("<div class='message_time'></div>");
                    message
                        .find(".message_time")
                        .animate({ width: "100%" }, (time + key + 1) * 1000, function () {
                            $(this).parents(".alert").fadeOut(200);
                        });
                    if(key > 0) {
                        setTimeout(() => {
                            $(className).append(message);
                            message.effect('bounce');
                        }, (300 * key));
                    } else {
                        $(className).append(message);
                        message.effect('bounce');
                    }
                });
                return;
            }

        }
        ajaxMessage.append("<div class='message_time'></div>");
        ajaxMessage
            .find(".message_time")
            .animate({ width: "100%" }, (time) * 1000, function () {
                $(this).parents(".alert").fadeOut(200);
            });

        $(className).append(ajaxMessage);
        ajaxMessage.effect("bounce");
    }
};

function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}