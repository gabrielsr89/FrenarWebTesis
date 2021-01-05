
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pagar con mercado pago</title>
    </head>
    <body id="fondomain">
        <div class="container text-center" style="width: 100%; color:ivory;">
            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom" style="margin-top: 60px!important;">
                <h1 class="titulares bg-secondary">Pagar a través de mercado pago</h1>
            </div>
            <div class="row">
                <div class="col">
                    <ul class="list-group text-center" style="color: ivory;">
                        <li class="list-group-item">
                            Cliente: ${nombreApellido}
                        </li>
                        <li class="list-group-item">
                            Monto: ${monto}
                        </li>
                        <li class="list-group-item">
                            <form method="POST" action="./PagarMercadoPagoServlet">
                                <input type="hidden" value="${id_f}" name="id_factura">
                                <input class="btn btn-dark" type="button" value="Pagar a través de mercado pago" onclick="Enviar()">
                                <script src="https://www.mercadopago.com.ar/integrations/v1/web-payment-checkout.js" data-preference-id="${preference.id}" data-button-label="Mercado Pago"></script>
                            </form>                            
                        </li>
                    </ul>
                </div>                
            </div>
        </div>
        <script type="text/javascript">
                                    function Enviar() {
                                        var btnMercado = document.getElementsByClassName('mercadopago-button');
                                        btnMercado[0].click();
                                    }
        </script>
    <c:import url="bootstrap.jsp"></c:import>
</body>
</html>
