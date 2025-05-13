package com.github.marcoadp.controle_investimentos.repository.spec;

import com.github.marcoadp.controle_investimentos.entity.FundoImobiliario;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class FundoImobiliarioSpec {

    public static Specification<FundoImobiliario> where(String codigo, String tipo, String segmento) {
        return Specification.where(hasCodigo(codigo))
                .and(hasTipo(tipo))
                .and(hasSegmento(segmento));
    }

    public static Specification<FundoImobiliario> hasCodigo(String codigo) {
        if (StringUtils.isBlank(codigo)) {
            return null;
        }
        return (root, query, cb) -> cb.equal(root.get("codigo"), codigo);
    }

    public static Specification<FundoImobiliario> hasTipo(String tipo) {
        if (StringUtils.isBlank(tipo)) {
            return null;
        }
        return (root, query, cb) -> cb.equal(root.get("tipo"), tipo);
    }

    public static Specification<FundoImobiliario> hasSegmento(String segmento) {
        if (StringUtils.isBlank(segmento)) {
            return null;
        }
        return (root, query, cb) -> cb.equal(root.get("segmento"), segmento);
    }

}
