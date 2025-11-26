package ru.simul.consts;

public enum Messages {
    START("===START SIMULATION==="),
    END("===END SIMULATION ==="),
    ON_PAUSE(">>> ПРИЛОЖЕНИЕ НА ПАУЗЕ <<<"),
    PRESS_SPASE_ENTER("Нажмите 'Пробел' и Enter для продолжения..."),
    CONTINUE(">>> ПРОДОЛЖЕНИЕ РАБОТЫ <<<"),
    STEP_SEPARATOR("_________________________"),
    COMMAND_PAUSE(">> Команда: ПАУЗА"),
    COMMAND_CONTINUE(">> Команда: ПРОДОЛЖИТЬ"),
    STATUS_LINE("трава: %d; кролики: %d; шаг = %d");

    private final String text;

    Messages(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
