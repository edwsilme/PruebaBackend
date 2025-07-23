package com.example.PruebaEdwslme.Integration;

import com.example.PruebaEdwslme.dto.RequestDto;
import com.example.PruebaEdwslme.dto.ResponseDto;
import com.example.PruebaEdwslme.exception.SoapExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class IntegrationSoap {

    /**
    public void guardarEmpleado(ResponseDto empleado) {
        System.out.println("Simulando llamada SOAP...");
        System.out.println("Guardando empleado vía SOAP:");
        System.out.println("Nombres: " + empleado.getNombres());
        System.out.println("Apellidos: " + empleado.getApellidos());
        System.out.println("Tipo de Documento: " + empleado.getTipoDocumento());
        System.out.println("Número de Documento: " + empleado.getNumeroDocumento());
        System.out.println("Fecha de Nacimiento: " + empleado.getFechaNacimiento());
        System.out.println("Edad Actual: " + empleado.getEdadActual());
        System.out.println("Fecha de Vinculación: " + empleado.getFechaVinculacion());
        System.out.println("Tiempo de Vinculación: " + empleado.getTiempoVinclulacion());
    } **/

    @Value("${soap.flag.success}")
    private boolean isSuccess;

    public ResponseDto enviarSolicitudSoap(RequestDto dto) {
        String xml = construirXmlSoap(dto);
        System.out.println("SOAP XML generado:\n" + xml);

        if (!isSuccess) {
            throw new SoapExceptionHandler("Error al procesar solicitud en soap");
        }

        return ResponseDto.builder()
                .success(isSuccess)
                .message("Solicitud exitosa")
                .build();
    }

    private String construirXmlSoap(RequestDto dto) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
                "                  xmlns:soap=\"https://soap.com/ejemplo\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <soap:Solicitud>\n" +
                "         <soap:Nombres>" + escape(dto.getNombres()) + "</soap:Nombres>\n" +
                "         <soap:Apellidos>" + escape(dto.getApellidos()) + "</soap:Apellidos>\n" +
                "         <soap:TipoDocumento>" + escape(dto.getTipoDocumento()) + "</soap:TipoDocumento>\n" +
                "         <soap:NumeroDocumento>" + escape(dto.getNumeroDocumento()) + "</soap:NumeroDocumento>\n" +
                "         <soap:FechaNacimiento>" + dto.getFechaNacimiento() + "</soap:FechaNacimiento>\n" +
                "         <soap:FechaVinculacion>" + dto.getFechaVinculacion() + "</soap:FechaVinculacion>\n" +
                "         <soap:Cargo>" + escape(dto.getCargo()) + "</soap:Cargo>\n" +
                "         <soap:Salario>" + dto.getSalario() + "</soap:Salario>\n" +
                "         <soap:EdadActual>" + escape(dto.getEdadActual()) + "</soap:EdadActual>\n" +
                "         <soap:TiempoVinculacion>" + escape(dto.getTiempoVinclulacion()) + "</soap:TiempoVinculacion>\n" +
                "      </soap:Solicitud>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
    }

    private String escape(String value) {
        if (value == null) return "";
        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }
}
