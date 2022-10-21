$(document).ready(function () {

        $("#buscar_camiseta").autocomplete({

            /* Muestra camisetas en el autocomplete */
            source: function (request, response) {
                $.ajax({
                    url: "/cargar-camisetas/" + request.term,
                    dataType: "json",
                    data: {
                        term: request.term
                    },
                    success: function (data) {
                        response($.map(data, function (item) {
                            return {
                                value: item.id,
                                label: item.nombre,
                                precio: item.precio,

                            };
                        }));
                    },
                })
            },

            /* Selecciona el valor */
            select: function (event, ui) {
                $("#buscar_camiseta").val(ui.item.label);
                return false;
            }

        });

    }

);