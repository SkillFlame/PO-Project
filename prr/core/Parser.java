package prr.core;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

import prr.core.exception.UnrecognizedEntryException;

/* 
 * A concretização desta classe depende da funcionalidade suportada pelas entidades do core:
 *  - adicionar um cliente e terminal a uma rede de terminais;
 *  - indicar o estado de um terminal
 *  - adicionar um amigo a um dado terminal
 * A forma como estas funcionalidades estão concretizaas terão impacto depois na concretização dos
 * métodos parseClient, parseTerminal e parseFriends
 */

public class Parser {
	private Network _network;

	Parser(Network network) {
		_network = network;
	}

	void parseFile(String filename) throws IOException, UnrecognizedEntryException {
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line;

			while ((line = reader.readLine()) != null)
				parseLine(line);
		}
	}

	private void parseLine(String line) throws UnrecognizedEntryException {
		String[] components = line.split("\\|");

		switch (components[0]) {
			case "CLIENT" -> parseClient(components, line);
			case "BASIC", "FANCY" -> parseTerminal(components, line);
			case "FRIENDS" -> parseFriends(components, line);
			default -> throw new UnrecognizedEntryException("Line with wrong type: " + components[0]);
		}
	}

	private void checkComponentsLength(String[] components, int expectedSize, String line)
			throws UnrecognizedEntryException {
		if (components.length != expectedSize)
			throw new UnrecognizedEntryException("Invalid number of fields in line: " + line);
	}

	// parse a client with format CLIENT|id|nome|taxId
	private void parseClient(String[] components, String line) throws UnrecognizedEntryException {
		checkComponentsLength(components, 4, line);

		try {
			int taxNumber = Integer.parseInt(components[3]);
			_network.registerClient(components[1], components[2], taxNumber);
		} catch (NumberFormatException nfe) {
			throw new UnrecognizedEntryException("Invalid number in line " + line, nfe);
		} catch (Exception e) {
			throw new UnrecognizedEntryException("Invalid specification in line: " + line, e);
		}
	}

	// parse a line with format terminal-type|idTerminal|idClient|state
	private void parseTerminal(String[] components, String line) throws UnrecognizedEntryException {
		checkComponentsLength(components, 4, line);

		try {
			Terminal terminal = _network.registerTerminal(components[0], components[1], components[2]);
			switch (components[3]) {
				case "SILENCE" -> terminal.setOnSilent();
				case "OFF" -> terminal.turnOff();
				default -> {
					if (!components[3].equals("ON"))
						throw new UnrecognizedEntryException("Invalid specification in line: " + line);
				}
			}
		} catch (Exception e) {
			throw new UnrecognizedEntryException("Invalid specification: " + line, e);
		}
	}

	// Parse a line with format FRIENDS|idTerminal|idTerminal1,...,idTerminalN
	private void parseFriends(String[] components, String line) throws UnrecognizedEntryException {
		checkComponentsLength(components, 3, line);

		try {
			String terminal = components[1];
			String[] friends = components[2].split(",");

			for (String friend : friends)
				_network.addFriend(terminal, friend);
		} catch (Exception e) {
			throw new UnrecognizedEntryException("Some message error in line:  " + line, e);
		}
	}
}