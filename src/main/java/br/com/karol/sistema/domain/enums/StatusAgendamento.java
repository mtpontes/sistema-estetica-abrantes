package br.com.karol.sistema.domain.enums;

import java.util.List;

public enum StatusAgendamento {
    PENDENTE {
        @Override
        public void validateTransition(StatusAgendamento newStatus) {
            if (!List.of(CONFIRMADO, CANCELADO).contains(newStatus))
                throw new IllegalArgumentException(String.format(getMessage() , this, newStatus)); 
        }
    },
    CONFIRMADO {
        @Override
        public void validateTransition(StatusAgendamento newStatus) {
            if (!List.of(CANCELADO, FINALIZADO).contains(newStatus))
                throw new IllegalArgumentException(String.format(getMessage() , this, newStatus)); 
        }
    },
    CANCELADO {
        @Override
        public void validateTransition(StatusAgendamento newStatus) {
            throw new IllegalArgumentException(String.format(getMessage() , this, newStatus));
        }
    },
    FINALIZADO {
        @Override
        public void validateTransition(StatusAgendamento newStatus) {
            throw new IllegalArgumentException(String.format(getMessage() , this, newStatus)); 
        }
    };

    public abstract void validateTransition(StatusAgendamento newStatus);
    private static String getMessage() {
        return "Não é possível alterar status %s para %s";
    }
}