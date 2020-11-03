package ru.hh.radar.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Test
    void shouldConvertHtmlToText() {
        // given
        String htmlText = "<p>Any text</p>";
        String formattingText = "Any text";
        // when
        String result = Utils.htmlToText(htmlText);
        // then
        assertEquals(formattingText, result);
    }

    @Test
    void shouldReturnFormattingStringFromLocalDateTime() {
        // given
        LocalDateTime sourceData = LocalDateTime.of(2020, 10, 1, 12, 0);
        String formattingData = "01-10-2020 12:00";
        // when
        String result = Utils.getFormattingData(sourceData);
        // then
        assertEquals(formattingData, result);
    }

    @Test
    void shouldReturnLocalDateTimeFromFormattingString() {
        // given
        String sourceData = "2020-10-16T18:04:59+0300";
        LocalDateTime expectedData = LocalDateTime.of(2020, 10, 16, 18, 4, 59);
        // when
        LocalDateTime result = Utils.getLocalDateTime(sourceData);
        // then
        assertEquals(expectedData, result);
    }

    @Test
    void shouldReturnCommandKeyFromCommand() {
        // given
        String command = "/start abcd efg";
        String commandKey = "/start";
        // when
        String result = Utils.getCommandKey(command);
        // then
        assertEquals(commandKey, result);
    }

    @Test
    void shouldReturnCommandValueFromCommand() {
        // given
        String command = "/start abcd efg";
        String commandValue = "abcd efg";
        // when
        String result = Utils.getCommandValue(command);
        // then
        assertEquals(commandValue, result);
    }

    @Test
    void shouldReturnTrueForClickableCommand() {
        // given
        String clickableCommand = "/any";
        // when
        boolean isClickableCommand = Utils.isClickableCommand(clickableCommand);
        // then
        assertTrue(isClickableCommand);
    }

    @Test
    void shouldReturnFalseForNotClickableCommand() {
        // given
        String notClickableCommand = "any";
        // when
        boolean isClickableCommand = Utils.isClickableCommand(notClickableCommand);
        // then
        assertFalse(isClickableCommand);
    }
}