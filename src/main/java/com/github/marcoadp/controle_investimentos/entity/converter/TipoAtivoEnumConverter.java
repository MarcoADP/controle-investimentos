package com.github.marcoadp.controle_investimentos.entity.converter;

import com.github.marcoadp.controle_investimentos.enums.TipoAtivoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipoAtivoEnumConverter implements AttributeConverter<TipoAtivoEnum, String> {

    @Override
    public String convertToDatabaseColumn(TipoAtivoEnum tipo) {
        return tipo != null ? tipo.getDescricao() : null;
    }

    @Override
    public TipoAtivoEnum convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        for (TipoAtivoEnum tipo : TipoAtivoEnum.values()) {
            if (tipo.getDescricao().equals(dbData)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("TipoAtivoEnum inválido: " + dbData);
    }
}