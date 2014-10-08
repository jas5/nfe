package com.fincatto.nfe.webservices;

import com.fincatto.nfe.NFeConfig;
import com.fincatto.nfe.classes.NFUnidadeFederativa;
import com.fincatto.nfe.classes.evento.NFEnviaEventoRetorno;
import com.fincatto.nfe.classes.lote.consulta.NFLoteConsultaRetorno;
import com.fincatto.nfe.classes.lote.envio.NFLoteEnvio;
import com.fincatto.nfe.classes.lote.envio.NFLoteEnvioRetorno;
import com.fincatto.nfe.classes.nota.consulta.NFNotaConsultaRetorno;
import com.fincatto.nfe.classes.statusservico.consulta.NFStatusServicoConsultaRetorno;
import com.fincatto.nfe.validadores.xsd.XMLValidador;

public class WSFacade {
    private final WSLoteEnvio wsLoteEnvio;
    private final WSLoteConsulta wsLoteConsulta;
    private final WSStatusConsulta wsStatusConsulta;
    private final WSNotaConsulta wsNotaConsulta;
    private final WSCartaCorrecao wsCartaCorrecao;
    private final WSCancelamento wsCancelamento;

    public WSFacade(final NFeConfig config) {
        System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
        System.setProperty("javax.net.ssl.trustStoreType", "JKS");
        System.setProperty("javax.net.ssl.trustStore", config.getCadeiaCertificados().getAbsolutePath());
        System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
        System.setProperty("javax.net.ssl.keyStore", config.getCertificado().getAbsolutePath());
        System.setProperty("javax.net.ssl.keyStorePassword", config.getCertificadoSenha());

        this.wsLoteEnvio = new WSLoteEnvio(config);
        this.wsLoteConsulta = new WSLoteConsulta(config);
        this.wsStatusConsulta = new WSStatusConsulta(config);
        this.wsNotaConsulta = new WSNotaConsulta(config);
        this.wsCartaCorrecao = new WSCartaCorrecao(config);
        this.wsCancelamento = new WSCancelamento(config);
    }

    public NFLoteEnvioRetorno enviaLote(final NFLoteEnvio lote, final NFUnidadeFederativa uf) throws Exception {
        XMLValidador.validaLote(lote.toString());
        return this.wsLoteEnvio.enviaLote(lote, uf);
    }

    public NFLoteConsultaRetorno consultaLote(final String numeroRecibo, final NFUnidadeFederativa uf) throws Exception {
        return this.wsLoteConsulta.consultaLote(numeroRecibo, uf);
    }

    public NFStatusServicoConsultaRetorno consultaStatus(final NFUnidadeFederativa uf) throws Exception {
        return this.wsStatusConsulta.consultaStatus(uf);
    }

    public NFNotaConsultaRetorno consultaNota(final String chaveDeAcesso, final NFUnidadeFederativa uf) throws Exception {
        return this.wsNotaConsulta.consultaNota(chaveDeAcesso, uf);
    }

    public NFEnviaEventoRetorno corrigeNota(final String chaveDeAcesso, final String textoCorrecao) throws Exception {
        return this.wsCartaCorrecao.corrigirNota(chaveDeAcesso, textoCorrecao);
    }

    public NFEnviaEventoRetorno cancelaNota(final String chaveAcesso, final String numeroProtocolo, final String motivo) throws Exception {
        return this.wsCancelamento.cancelaNota(chaveAcesso, numeroProtocolo, motivo);
    }
}