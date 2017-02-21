/*
 * libajp13 - AjpMessageTest.java
 *
 * Copyright (c) 2017 Luca Carettoni - Doyensec LLC. 
 * Copyright (c) 2010 Espen Wiborg
 *
 * Licensed under the Apache License, Version 2.0
 */
package com.doyensec.ajp13;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test cases for all AJP messages
 */
public class AjpMessageTest
{

    @Test
    public void cpingMessageIsTypeRequest() throws Exception
    {
        AjpMessage cping = new CPingMessage();
        byte[] cpingBytes = cping.getBytes();
        assertThat(cpingBytes[0], is((byte) 18));
        assertThat(cpingBytes[1], is((byte) 52));
    }

    @Test
    public void cpingMessageIsCorrectLength() throws Exception
    {
        AjpMessage cping = new CPingMessage();
        byte[] cpingBytes = cping.getBytes();
        assertThat(cpingBytes.length, is(5));
    }

    @Test
    public void cpingMessagePayloadLengthIsOne() throws Exception
    {
        AjpMessage cping = new CPingMessage();
        byte[] cpingBytes = cping.getBytes();
        assertThat(cpingBytes[2], is((byte) 0));
        assertThat(cpingBytes[3], is((byte) 1));
    }

    @Test
    public void cpongMessageIsTypeResponse() throws Exception
    {
        AjpMessage cpong = new CPongMessage();
        byte[] cpongBytes = cpong.getBytes();
        assertThat(cpongBytes[0], is((byte) 65));
        assertThat(cpongBytes[1], is((byte) 66));
    }

    @Test
    public void cpongMessageIsCorrectLength() throws Exception
    {
        AjpMessage cpong = new CPongMessage();
        byte[] cpongBytes = cpong.getBytes();
        assertThat(cpongBytes.length, is(5));
    }

    @Test
    public void cpongMessagePayloadLengthIsOne() throws Exception
    {
        AjpMessage cpong = new CPongMessage();
        byte[] cpongBytes = cpong.getBytes();
        assertThat(cpongBytes[2], is((byte) 0));
        assertThat(cpongBytes[3], is((byte) 1));
    }

    @Test
    public void shutdownMessageIsTypeRequest() throws Exception
    {
        AjpMessage shutdown = new ShutdownMessage();
        byte[] shutdownBytes = shutdown.getBytes();
        assertThat(shutdownBytes[0], is((byte) 18));
        assertThat(shutdownBytes[1], is((byte) 52));
    }

    @Test
    public void shutdownMessageIsCorrectLength() throws Exception
    {
        AjpMessage shutdown = new ShutdownMessage();
        byte[] shutdownBytes = shutdown.getBytes();
        assertThat(shutdownBytes.length, is(5));
    }

    @Test
    public void shutdownMessagePayloadLengthIsOne() throws Exception
    {
        AjpMessage shutdown = new ShutdownMessage();
        byte[] shutdownBytes = shutdown.getBytes();
        assertThat(shutdownBytes[2], is((byte) 0));
        assertThat(shutdownBytes[3], is((byte) 1));
    }

    @Test
    public void pingMessageIsTypeRequest() throws Exception
    {
        AjpMessage ping = new PingMessage();
        byte[] pingBytes = ping.getBytes();
        assertThat(pingBytes[0], is((byte) 18));
        assertThat(pingBytes[1], is((byte) 52));
    }

    @Test
    public void pingMessageIsCorrectLength() throws Exception
    {
        AjpMessage ping = new PingMessage();
        byte[] pingBytes = ping.getBytes();
        assertThat(pingBytes.length, is(5));
    }

    @Test
    public void pingMessagePayloadLengthIsOne() throws Exception
    {
        AjpMessage ping = new PingMessage();
        byte[] pingBytes = ping.getBytes();
        assertThat(pingBytes[2], is((byte) 0));
        assertThat(pingBytes[3], is((byte) 1));
    }

    @Test
    public void endResponseMessageIsTypeResponse() throws Exception
    {
        AjpMessage endResponse = new EndResponseMessage(true);
        byte[] endResponseBytes = endResponse.getBytes();
        assertThat(endResponseBytes[0], is((byte) 65));
        assertThat(endResponseBytes[1], is((byte) 66));
    }

    @Test
    public void endResponseMessagePayloadLengthIsTwo() throws Exception
    {
        AjpMessage endResponse = new EndResponseMessage(false);
        byte[] endResponseBytes = endResponse.getBytes();
        assertThat(endResponseBytes[2], is((byte) 0));
        assertThat(endResponseBytes[3], is((byte) 2));
    }

    @Test
    public void sendBodyChunkMessageIsTypeResponse() throws Exception
    {
        AjpMessage sendBodyChunk = new SendBodyChunkMessage("ABCD".getBytes());
        byte[] sendBodyChunkBytes = sendBodyChunk.getBytes();
        assertThat(sendBodyChunkBytes[0], is((byte) 65));
        assertThat(sendBodyChunkBytes[1], is((byte) 66));
    }

    @Test
    public void sendBodyChunkMessageIsCorrectLength() throws Exception
    {
        AjpMessage sendBodyChunk = new SendBodyChunkMessage("ABCD".getBytes());
        byte[] sendBodyChunkBytes = sendBodyChunk.getBytes();
        assertThat(sendBodyChunkBytes[2], is((byte) 0)); //payload size
        assertThat(sendBodyChunkBytes[3], is((byte) 7));
        //We assume no trailing null byte. In some implementations, it's null-byte terminated!?
        assertThat(sendBodyChunkBytes[5], is((byte) 0)); //binary data size.  
        assertThat(sendBodyChunkBytes[6], is((byte) 4));
        assertThat(sendBodyChunkBytes.length, is(11)); //total packet size
    }

    @Test
    public void sendHeadersMessageIsTypeResponse() throws Exception
    {
        List<Pair<String, String>> headers = new LinkedList<>();
        headers.add(Pair.make("HeaderName", "HeaderValue"));
        AjpMessage sendHeaders = new SendHeadersMessage(404, "NOT FOUND", headers);
        byte[] sendHeadersBytes = sendHeaders.getBytes();
        assertThat(sendHeadersBytes[0], is((byte) 65));
        assertThat(sendHeadersBytes[1], is((byte) 66));
    }

    @Test
    public void sendHeadersMessageIsCorrectLength() throws Exception
    {
        List<Pair<String, String>> headers = new LinkedList<>();
        headers.add(Pair.make("A", "B"));
        AjpMessage sendHeaders = new SendHeadersMessage(200, "OK", headers);
        byte[] sendHeadersBytes = sendHeaders.getBytes();
        assertThat(sendHeadersBytes.length, is(22));
    }

    @Test
    public void sendHeadersMessageNumberOfHeadersIsThree() throws Exception
    {
        List<Pair<String, String>> headers = new LinkedList<>();
        headers.add(Pair.make("HeaderName1", "HeaderValue1"));
        headers.add(Pair.make("HeaderName2", "HeaderValue2"));
        headers.add(Pair.make("HeaderName3", "HeaderValue3"));
        AjpMessage sendHeaders = new SendHeadersMessage(200, "OK", headers);
        byte[] sendHeadersBytes = sendHeaders.getBytes();
        assertThat(sendHeadersBytes[12], is((byte) 0));
        assertThat(sendHeadersBytes[13], is((byte) 3));
    }

    @Test
    public void getBodyChunkMessageIsTypeResponse() throws Exception
    {
        AjpMessage getBodyChunk = new GetBodyChunkMessage(12);
        byte[] getBodyChunkBytes = getBodyChunk.getBytes();
        assertThat(getBodyChunkBytes[0], is((byte) 65));
        assertThat(getBodyChunkBytes[1], is((byte) 66));
    }

    @Test
    public void getBodyChunkMessageIsCorrectLength() throws Exception
    {
        AjpMessage getBodyChunk = new GetBodyChunkMessage(12);
        byte[] getBodyChunkBytes = getBodyChunk.getBytes();
        assertThat(getBodyChunkBytes.length, is(7));
    }

    @Test
    public void bodyMessageIsTypeRequest() throws Exception
    {
        AjpMessage body = new BodyMessage("foobar".getBytes());
        byte[] bodyBytes = body.getBytes();
        assertThat(bodyBytes[0], is((byte) 18));
        assertThat(bodyBytes[1], is((byte) 52));
    }

    @Test
    public void bodyMessageIsCorrectLength() throws Exception
    {
        AjpMessage body = new BodyMessage("foobar".getBytes());
        byte[] bodyBytes = body.getBytes();
        assertThat(bodyBytes.length, is(12));
    }
    
    @Test
    public void bodyMessageIsEmpty() throws Exception
    {
        AjpMessage body = new BodyMessage(new byte[0]);
        byte[] bodyBytes = body.getBytes();
        assertThat(bodyBytes.length, is(4));
    }
    
    @Test
    public void bodyMessageContentDump() throws Exception
    {
        AjpMessage body = new BodyMessage("AAAA".getBytes());
        assertThat(body.getDescription(), is("Remaining request body data."
                + "\nContent (HEX):\n0x41414141\nContent (Ascii):\nAAAA"));
    }

    @Test
    public void bodyMessagePacketLengthIsPayloadPlusTwo() throws Exception
    {
        AjpMessage body = new BodyMessage("foobar".getBytes());
        byte[] bodyBytes = body.getBytes();
        int sizePayload = AjpReader.makeInt(bodyBytes[4], bodyBytes[5]);
        int sizePacket = AjpReader.makeInt(bodyBytes[2], bodyBytes[3]);
        assertThat(sizePayload + 2, is(sizePacket));
    }

    @Test
    public void ForwardRequestMessageIsTypeRequest() throws Exception
    {
        List<Pair<String, String>> headers = new LinkedList<>();
        headers.add(Pair.make("Content-Type", "text/html; charset=utf-8"));
        AjpMessage forwardRequest = new ForwardRequestMessage(2, new URL("http://127.0.0.1/"), headers, null);
        byte[] forwardRequestBytes = forwardRequest.getBytes();
        assertThat(forwardRequestBytes[0], is((byte) 18));
        assertThat(forwardRequestBytes[1], is((byte) 52));
    }

    @Test
    public void ForwardRequestMessageIsCorrectLength() throws Exception
    {
        List<Pair<String, String>> headers = new LinkedList<>();
        headers.add(Pair.make("Content-Type", "text/html; charset=utf-8"));
        AjpMessage forwardRequest = new ForwardRequestMessage(2, new URL("http://127.0.0.1/"), headers, null);
        byte[] forwardRequestBytes = forwardRequest.getBytes();
        assertThat(forwardRequestBytes.length, is(89));

        forwardRequest = ForwardRequestMessage.ForwardRequestMessageGetBuilder(new URL("http://192.168.1.1/log/"));
        forwardRequestBytes = forwardRequest.getBytes();
        assertThat(forwardRequestBytes.length, is(85));

        forwardRequest = ForwardRequestMessage.ForwardRequestMessagePostBuilder(new URL("http://192.168.1.1/log/"), 80);
        forwardRequestBytes = forwardRequest.getBytes();
        assertThat(forwardRequestBytes.length, is(111));

        headers.add(Pair.make("CustomHeaderName", "CustomHeaderValue"));
        List<Pair<String, String>> attributes = new LinkedList<>();
        attributes.add(Pair.make("jvm_route", "3131212"));
        attributes.add(Pair.make("custom_attribute", "custom_value"));
        forwardRequest = new ForwardRequestMessage(5, "HTTP/1.0", "/api/", "127.0.0.1", "localhost", "127.0.0.1", 8009, true, headers, attributes);
        forwardRequestBytes = forwardRequest.getBytes();
        assertThat(forwardRequestBytes.length, is(181));
    }

    @Test
    public void ForwardRequestMessageNumberOfHeadersIsFour() throws Exception
    {
        List<Pair<String, String>> headers = new LinkedList<>();
        headers.add(Pair.make("HeaderName1", "HeaderValue1"));
        headers.add(Pair.make("HeaderName2", "HeaderValue2"));
        headers.add(Pair.make("HeaderName3", "HeaderValue3"));
        headers.add(Pair.make("HeaderName4", "HeaderValue4"));
        AjpMessage forwardRequest = new ForwardRequestMessage(10, new URL("http://127.0.0.1/"), headers, null);
        byte[] forwardRequestBytes = forwardRequest.getBytes();
        assertThat(forwardRequestBytes[58], is((byte) 4));
    }
}