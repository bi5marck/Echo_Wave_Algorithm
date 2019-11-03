package uk.ac.ncl.Z_Wu.Echo_Wave_Algorithm;

/**
 * This programme uses a N*N matrix to emulate inter-node communication. So this is a matrix class
 * which provides many methods for algorithm to use.
 */

class Execution_Matrix {
    private int column;
    private int row;
    private Integer[][] execution_matrix;

    /**
     * This is constructor method.
     *
     * @param column           is the matrix's column number.
     * @param row              is the matrix's row number.
     * @param execution_matrix is matrix itself and all of it's value will be set to 0.
     */

    Execution_Matrix(int column, int row, Integer[][] execution_matrix) {
        this.column = column;
        this.row = row;
        this.execution_matrix = execution_matrix;
        for (int i = 0; i < row; i++)
            for (int j = 0; j < column; j++)
                execution_matrix[i][j] = 0;
    }

    /**
     * This method can set one element of matrix to given number.
     * When node A send a message to node B, then set matrix[A][B] = 1.
     *
     * @param row    is sender's node name.
     * @param column is receiver's node name
     * @param value  equals 1 means send a message.
     */
    void setExecution_matrix(int row, int column, Integer value) {
        this.execution_matrix[row][column] = value;
    }

    /**
     * This method determines currently if matrix is a zero-matrix.
     * Zero-matrix means no one has been waken up.
     * This method calculates the sum of all elements and result is 0 means it's a zero-matrix.
     *
     * @return if matrix is a zero-matrix.
     */
    boolean hasInitiator() {
        int sum = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (execution_matrix[i][j] != 0)
                    sum = sum + execution_matrix[i][j];
            }
        }
        return (sum == 0);
    }

    /**
     * This method calculate the sum of given column,which also means the total number of messages
     * the given node received.
     *
     * @param node is the column as well as one particular node.
     * @return the sum of given column as well as the total number of messages the given node received.
     */
    int getNodeReceive(int node) {
        int sum = 0;
        for (int i = 0; i < row; i++) {
            sum = sum + execution_matrix[i][node];
        }
        return sum;
    }

    /**
     * This method calculate the sum of given row,which also means the total number of messages
     * the given node sent.
     *
     * @param node is the row as well as one particular node.
     * @return the sum of given row as well as the total number of messages the given node sent.
     */
    int getNodeSend(int node) {
        int sum = 0;
        for (int i = 0; i < column; i++) {
            sum = sum + execution_matrix[node][i];
        }
        return sum;
    }

    /**
     * This method decides which message will be received firstly when one particular node receives
     * more than one messages.
     * In this method, the mechanism of receiving multiple messages is receiving smallest node's message.
     *
     * @param node is the column as well as one particular node.
     * @return the name of first message's sender.
     */
    int getFirstSender(int node) {
        Integer integer = 0;

        for (int i = 0; i < row; i++) {
            integer = i;
            if (execution_matrix[i][node] != 0)
                break;
            else
                integer = null;
        }

        return integer;
    }

    /**
     * This method calculates the sum of each element of matrix.
     * The sum of each element means the total messages sent.
     *
     * @return the sum of each element.
     */
    Integer node_sum() {
        int sum = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (execution_matrix[i][j] != 0)
                    sum = sum + execution_matrix[i][j];
            }
        }
        return sum;
    }

    /**
     * This method prints the whole matrix and it also prints the top line and left side
     * of matrix so that the output of matrix can be more intuitive.
     */
    void print() {
        System.out.print("    ");
        for (int a = 0; a < row; a++) {
            System.out.print(a + " ");
        }
        System.out.println("\n");
        for (int i = 0; i < row; i++) {
            System.out.print(i + "   ");
            for (int j = 0; j < column; j++) {
                System.out.print(execution_matrix[i][j] + " ");
            }
            System.out.print("\n");
        }
    }


}
