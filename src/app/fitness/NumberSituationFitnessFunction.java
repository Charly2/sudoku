/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.fitness;

/**
 *
 * @author Charly
 */
import app.exception.BuilderException;
import app.fitness.classes.SudokuBoard;


import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

public class NumberSituationFitnessFunction extends FitnessFunction {

    
    private static final double INVALID = 0;
    private SudokuBoard sudokuProblem;
    private int dimension;

    public NumberSituationFitnessFunction(int dimension, final int[] sudokuProblem) throws BuilderException {
        this.dimension = dimension;
        this.sudokuProblem = new SudokuBoard(dimension, sudokuProblem);
    }

   

    @Override
    protected double evaluate(IChromosome cromosoma) {
        SudokuBoard individual;
        double fitness = INVALID;

        try {
            individual = createSudokuFromChromosome(dimension, cromosoma);
        } catch (BuilderException be) {
            System.out.println("The sudoku cannot be build"+ be.toString());
            return INVALID;
        }

        if (individual.canSolveSudokuTemplate(sudokuProblem)) {
//            fitness += individual.calculateNonDuplicatedValuesInSquares();
            fitness += individual.calculateNonDuplicatedValuesInLines();
            fitness += individual.calculateNonDuplicatedValuesInColumns();
        }
        return fitness;
    }

    public static SudokuBoard createSudokuFromChromosome(int dimension, final IChromosome cromosoma) throws BuilderException {

        int amountOfNumbersInBoard = SudokuBoard.amountOfNumbersInBoard(dimension);
        int[] genes = new int[amountOfNumbersInBoard];

        for (int i = 0; i < amountOfNumbersInBoard; i++) {
            genes[i] = ((Integer) cromosoma.getGene(i).getAllele()).intValue();
        }

        return new SudokuBoard(dimension, genes);
    }
}
