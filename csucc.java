import java.util.Scanner;
/*
 * Author: 	Alex Chengelis
 * ID:		2632220
 * Date:	12/2/15
 * HW:		CIS 335 Project 7 top down recursive compiler	
 */
public class csucc {
	static int REGA = 0;
	static int idx = 0;
	static int idindex = 0;
	static int tokid = 0;
	static int inindex = 0;
	static int getas = 1;
	static int inputcount = 0;
	static String[] S = new String[64];
	static String[] ids = new String[64];
	static String[] input = new String[64];
	static String rA = "rA";
	static String token;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		while (in.hasNext()) {
			input[inputcount++] = in.next();
		}

		in.close();

		token = input[inindex];

		statement();

		System.out.println();

		int z = 0;
		while (ids[z] != null) {
			z++;
		}
		for (int i = 0; i < z; i++) {
			System.out.println(ids[i] + "\tRESW\t1");
		}

	}

	public static void statement() {
		int i, idi;
		if (isId()) {
			while (token != null) {
				S[++idx] = token;
				idi = idx;
				ids[idindex++] = token;
				lexme();
				match(); // look for equals
				i = expression();
				GETA(i);
				System.out.println("\tMOV\t%EAX," + S[idi]);
				REGA = 0;
				match(); // look for semicolon

			}
		}
	}

	// checks for all the characters that would just mean to skip
	private static void match() {
		if (token != null) {
			if (token.equals("=") || token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/")
					|| token.equals(")") || token.equals(";")) {
				lexme();
			} else {
				System.out.println("Error");
			}
		}

	}

	public static void GETA(int n) {
		if (REGA == 0) {
			if (token != null) {
				System.out.println("\tMOV\t" + S[n] + ",%EAX");
			}
		} else if (!S[n].equals(rA)) {
			// create a new temp variable Ti generate STA ti

			// create head of temp variable
			String T = "T" + String.valueOf(getas);
			getas++; // increase the temp variable counter
			System.out.println("\tMOV\t%EAX," + T);
			S[REGA] = T; // store the new variable into the S array
			System.out.println("\tMOV\t" + S[n] + ",%EAX"); // print
															// corresponding
															// SICXE command
			ids[idindex++] = T; // add the temp variable into the ids list

		}
		S[n] = rA;
		REGA = n;
	}

	// advance the token
	public static void lexme() {
		token = input[++tokid];

	}

	public static int term() {
		int i, j;
		char op;

		i = factor();

		while (token.equals("*") || token.equals("/")) {
			op = token.charAt(0);
			lexme();
			j = factor();

			switch (op) {
			case '*':
				if (S[i].equals(rA)) {
					// gen add S[j]
					if (Character.isAlphabetic(S[j].charAt(0))) {
						System.out.println("\tMUL\t" + S[j]);
					} else {
						System.out.println("\tMUL\t#" + S[j]);
					}

				} else if (S[j].equals(rA)) {
					// gen addd [i]
					if (Character.isAlphabetic(S[j].charAt(0))) {
						System.out.println("\tMUL\t" + S[i]);
					} else {
						System.out.println("\tMUL\t#" + S[i]);
					}

				} else {
					GETA(i);
					// Generate add S[j]
					if (Character.isAlphabetic(S[j].charAt(0))) {
						System.out.println("\tMUL\t" + S[j]);
					} else {
						System.out.println("\tMUL\t" + S[j]);
					}
				}

				break; // break * case

			case '/':
				if (S[i].equals(rA)) {
					// gen sub s[j]
					if (Character.isAlphabetic(S[j].charAt(0))) {
						System.out.println("\tDIV\t" + S[j]);
					} else {
						System.out.println("\tDIV\t" + S[j]);
					}
				} else {
					GETA(i);
					// gen sub S[j]
					if (Character.isAlphabetic(S[j].charAt(0))) {
						System.out.println("\tDIV\t" + S[j]);
					} else {
						System.out.println("\tDIV\t" + S[j]);
					}
				}
				break;
			}

			S[i] = rA;
			REGA = i;
		} // end while

		return i;
	}

	public static int expression() {
		// System.out.println("in expression");
		int i, j;
		char op;

		i = term();

		while (token.equals("+") || token.equals("-")) {
			op = token.charAt(0);
			lexme();
			j = term();

			switch (op) {
			case '+':
				if (S[i].equals(rA)) {
					// gen add S[j]
					if (Character.isAlphabetic(S[j].charAt(0))) {
						System.out.println("\tADD\t" + S[j]);
					} else {
						System.out.println("\tADD\t#" + S[j]);
					}

				} else if (S[j].equals(rA)) {
					// gen addd [i]
					if (Character.isAlphabetic(S[j].charAt(0))) {
						System.out.println("\tADD\t" + S[i]);
					} else {
						System.out.println("\tADD\t#" + S[i]);
					}

				} else {
					GETA(i);
					// Generate add S[j]
					if (Character.isAlphabetic(S[j].charAt(0))) {
						System.out.println("\tADD\t" + S[j]);
					} else {
						System.out.println("\tADD\t#" + S[j]);
					}
				}

				break; // break + case

			case '-':
				if (S[i].equals(rA)) {
					// gen sub s[j]
					if (Character.isAlphabetic(S[j].charAt(0))) {
						System.out.println("\tSUB\t" + S[j]);
					} else {
						System.out.println("\tSUB\t#" + S[j]);
					}
				} else {
					GETA(i);
					// gen sub S[j]
					if (Character.isAlphabetic(S[j].charAt(0))) {
						System.out.println("\tSUB\t" + S[j]);
					} else {
						System.out.println("\tSUB\t#" + S[j]);
					}
				}
				break;
			}

			S[i] = rA;
			REGA = i;
		} // end while

		return i;
	}

	public static int factor() {

		int i;

		if (Character.isLetter(token.charAt(0))) {
			S[++idx] = token;
			lexme();
			return idx;
		} else if (Character.isDigit(token.charAt(0))) {
			S[++idx] = "#" + token;
			lexme();
			return idx;
		} else if (token.charAt(0) == '(') {
			lexme();
			i = expression();
			match();
			if (S[i].equals(rA))
				REGA = i;

			return i;
		} else {
			System.out.println("error");
			return idx; // nonsense variable meaning the program failed
		}
	}

	public static boolean isId() {
		char[] chars = token.toCharArray();
		for (char c : chars) {
			if (!Character.isLetter(c))
				return false;
		}
		return true;
	}

	public static boolean isNum() {
		for (int i = 0; i < token.length(); i++) {
			if (Character.isDigit(token.charAt(i))) {
				continue;
			} else
				return false;
		}
		return true;
	}

}
