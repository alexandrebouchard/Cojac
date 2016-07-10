package com.github.cojac.utils;

import java.io.File;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.cojac.Arg;
import com.github.cojac.Args;

public class InstructionWriter {

    private static String ROOT_ELT = "classes";
    private static String CLASS_ELT = "class";
    private static String METHOD_ELT = "method";
    private static String LINE_ELT = "line";
    private static String INSTRUCTION_ELT = "instruction";
    private static String CLASS_NAME_ATTR = "className";
    private static String METHOD_NAME_ATTR = "methodName";
    private static String LINE_NUMBER_ATTR = "lineNumber";
    private static String INSTRUCTION_NUMBER_ATTR = "instructionNumber";
    private static String OP_CODE_ATTR = "opCode";
    private static String OP_NAME_ATTR = "opName";
    private static String INVOKED_METHOD = "invokedMethod";
    private static String BEHAVIOUR_ATTR = "behaviour";
    private static String DEFAULT_BEHAVIOUR_VALUE = "IGNORE";

    // private HashMap<String, HashMap<String, HashMap<Integer, Instruction>>>
    // instructionMap;
    private HashMap<String, HashMap<String, HashMap<Integer, HashMap<Integer, InstructionMeta>>>> classMap;
    private static InstructionWriter INSTANCE;

    private InstructionWriter() {
        classMap = new HashMap<>();
    }

    public static InstructionWriter getinstance() {
        if (INSTANCE == null) {
            INSTANCE = new InstructionWriter();
        }
        return INSTANCE;
    }

    public void writeInstructionDocumentToFile(Args args) {
        try {
            // get the path to XML file
            String filePath = args.getValue(Arg.LISTING_INSTRUCTIONS);
            // ----------------------------------------------------------------------
            // prepare the XML document
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document document = docBuilder.newDocument();
            // ----------------------------------------------------------------------
            // fill the XML document with instructions
            Element rootElement = document.createElement(ROOT_ELT);
            classMapToXML(document, rootElement, classMap);
            document.appendChild(rootElement);
            //
            // Element rootElement = document.createElement(ROOT_ELT);
            // for (String className : classMap.keySet()) {
            // Element classElement = document.createElement(CLASS_ELT);
            // classElement.setAttribute(CLASS_NAME_ATTR, className);
            // for (String methodName : classMap.get(className).keySet()) {
            // Element methodElement = document.createElement(METHOD_ELT);
            // methodElement.setAttribute(METHOD_NAME_ATTR, methodName);
            // for (Integer lineNumber :
            // classMap.get(className).get(methodName).keySet()) {
            // Element lineElement = document.createElement(LINE_ELT);
            // lineElement.setAttribute(LINE_NUMBER_ATTR,
            // lineNumber.toString());
            // for (Integer instructionNumber :
            // classMap.get(className).get(methodName).get(lineNumber).keySet())
            // {
            // Element instructionElement =
            // document.createElement(INSTRUCTION_ELT);
            // instructionElement.setAttribute(INSTRUCTION_NUMBER_ATTR,
            // instructionNumber.toString());
            // InstructionMeta instructionMeta =
            // classMap.get(className).get(methodName).get(lineNumber).get(instructionNumber);
            //
            // instructionElement.setAttribute(OP_CODE_ATTR, "" +
            // instructionMeta.getOpCode());
            // instructionElement.setAttribute(OP_NAME_ATTR,
            // instructionMeta.getOpName());
            //
            // instructionElement.setAttribute(INVOKED_METHOD,
            // instructionMeta.getInvokedMethod());
            // instructionElement.setAttribute(BEHAVIOUR_ATTR,
            // instructionMeta.getBehaviour());
            // lineElement.appendChild(instructionElement);
            // }
            // methodElement.appendChild(lineElement);
            // }
            // classElement.appendChild(methodElement);
            // }
            // rootElement.appendChild(classElement);
            // }
            // document.appendChild(rootElement);

            // Element instructionMapElement =
            // document.createElement("instructionMap");
            // for (String className : instructionMap.keySet()) {
            // Element classMapElement = document.createElement("classMap");
            // classMapElement.setAttribute("className", className);
            // for (String methodName : instructionMap.get(className).keySet())
            // {
            // Element methodMapElement = document.createElement("methodMap");
            // methodMapElement.setAttribute("methodName", methodName);
            // for (Integer localInstructionNumber :
            // instructionMap.get(className).get(methodName).keySet()) {
            // Element instructionElement =
            // document.createElement("instruction");
            // Instruction instruction =
            // instructionMap.get(className).get(methodName).get(localInstructionNumber);
            // instructionElement.setAttribute("opCode", "" +
            // instruction.getOpCode());
            // instructionElement.setAttribute("opName", "" +
            // instruction.getOpName());
            // instructionElement.setAttribute("lineNumber", "" +
            // instruction.getLineNumber());
            // instructionElement.setAttribute("globalInstructionNumber", "" +
            // instruction.getGlobalInstructionNumber());
            // instructionElement.setAttribute("localInstructionNumber", "" +
            // instruction.getLocalInstructionNumber());
            // instructionElement.setAttribute("invokedMethod", "" +
            // instruction.getInvokedMethod());
            // instructionElement.setAttribute("behaviour", "" +
            // instruction.getBehaviour());
            // methodMapElement.appendChild(instructionElement);
            // }
            // classMapElement.appendChild(methodMapElement);
            // }
            // instructionMapElement.appendChild(classMapElement);
            // }
            // document.appendChild(instructionMapElement);
            // ----------------------------------------------------------------------
            // prepare to write the XML document
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(filePath));
            // ----------------------------------------------------------------------
            // write the XML document to XML file
            transformer.transform(source, result);
            // ----------------------------------------------------------------------
        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TransformerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void classMapToXML(Document document, Element rootElement, HashMap<String, HashMap<String, HashMap<Integer, HashMap<Integer, InstructionMeta>>>> classMap) {

        for (String className : classMap.keySet()) {
            Element classElement = document.createElement(CLASS_ELT);
            classElement.setAttribute(CLASS_NAME_ATTR, className);
            methodMapToXML(document, classElement, classMap.get(className));
            rootElement.appendChild(classElement);
        }
    }

    private void methodMapToXML(Document document, Element classElement, HashMap<String, HashMap<Integer, HashMap<Integer, InstructionMeta>>> methodMap) {
        for (String methodName : methodMap.keySet()) {
            Element methodElement = document.createElement(METHOD_ELT);
            methodElement.setAttribute(METHOD_NAME_ATTR, methodName);
            lineMapToXML(document, methodElement, methodMap.get(methodName));
            classElement.appendChild(methodElement);
        }

    }

    private void lineMapToXML(Document document, Element methodElement, HashMap<Integer, HashMap<Integer, InstructionMeta>> lineMap) {

        for (Integer lineNumber : lineMap.keySet()) {
            Element lineElement = document.createElement(LINE_ELT);
            lineElement.setAttribute(LINE_NUMBER_ATTR, lineNumber.toString());
            instructionMapToXML(document, lineElement, lineMap.get(lineNumber));
            methodElement.appendChild(lineElement);
        }

    }

    private void instructionMapToXML(Document document, Element lineElement, HashMap<Integer, InstructionMeta> instructionMap) {

        for (Integer instructionNumber : instructionMap.keySet()) {
            Element instructionElement = document.createElement(INSTRUCTION_ELT);
            instructionElement.setAttribute(INSTRUCTION_NUMBER_ATTR, instructionNumber.toString());
            InstructionMeta instructionMeta = instructionMap.get(instructionNumber);

            instructionElement.setAttribute(OP_CODE_ATTR, "" +
                    instructionMeta.getOpCode());
            instructionElement.setAttribute(OP_NAME_ATTR, instructionMeta.getOpName());

            instructionElement.setAttribute(INVOKED_METHOD, instructionMeta.getInvokedMethod());
            instructionElement.setAttribute(BEHAVIOUR_ATTR, instructionMeta.getBehaviour());
            lineElement.appendChild(instructionElement);
        }

    }

    // public synchronized void putClassMap(String className, HashMap<String,
    // HashMap<Integer, Instruction>> classMap) {
    // instructionMap.put(className, classMap);
    // }

    public synchronized void logInstruction(String className, String methodName, int lineNumber, int instructionNumber, int opCode, String opName, String invokedMethod) {
        if (!classMap.containsKey(className))
            classMap.put(className, new HashMap<>());
        if (!classMap.get(className).containsKey(methodName))
            classMap.get(className).put(methodName, new HashMap<>());
        if (!classMap.get(className).get(methodName).containsKey(lineNumber))
            classMap.get(className).get(methodName).put(lineNumber, new HashMap<>());
        if (!classMap.get(className).get(methodName).get(lineNumber).containsKey(instructionNumber))
            classMap.get(className).get(methodName).get(lineNumber).put(instructionNumber, new InstructionMeta(opCode, opName, invokedMethod, DEFAULT_BEHAVIOUR_VALUE));
    }
}
