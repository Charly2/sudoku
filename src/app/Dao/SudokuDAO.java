/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.Dao;

import app.configuration.SudokuConfiguration;
import app.exception.BuilderException;
import app.fitness.NumberSituationFitnessFunction;
import app.fitness.classes.SudokuBoard;
import static app.fitness.classes.SudokuBoard.SEPARATOR;
import java.text.DecimalFormat;
import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.IntegerGene;
import view.Texto;

/**
 *
 * @author Charly
 */
public class SudokuDAO {
    private static final int DIMENSION = 3;
    private static final int POPULATION_SIZE = 50;
    /*private static final int[] TEMPLATE_SUDOKU_3X3 = {
        0, 6, 0, 1, 0, 4, 0, 5, 0,
        0, 0, 8, 3, 0, 5, 6, 0, 0,
        2, 0, 0, 0, 0, 0, 0, 0, 1,
        8, 0, 0, 4, 0, 7, 0, 0, 6,
        0, 0, 6, 0, 0, 0, 3, 0, 0,
        7, 0, 0, 9, 0, 1, 0, 0, 4,
        5, 0, 0, 0, 0, 0, 0, 0, 2,
        0, 0, 7, 2, 0, 6, 9, 0, 0,
        0, 4, 0, 5, 0, 8, 0, 7, 0,
    };*/
    
    private static final int[] TEMPLATE_SUDOKU_3X3 = {
        0, 0, 6, 4, 2, 8, 0, 0, 0,
        4, 5, 0, 1, 7, 6, 0, 3, 2,
        0, 8, 7, 3, 9, 5, 0, 4, 1,
        0, 9, 3, 5, 8, 0, 7, 0, 0,
        0, 4, 0, 2, 0, 7, 0, 9, 3,
        7, 2, 0, 9, 0, 0, 5, 6, 0,
        5, 6, 8, 0, 3, 4, 2, 0, 9,
        0, 1, 0, 8, 0, 0, 3, 7, 6,
        0, 7, 0, 6, 1, 0, 4, 8, 0
    };
    private static final int[] SUDOKU = TEMPLATE_SUDOKU_3X3;
    
    public static Texto getAll() throws BuilderException, InvalidConfigurationException{
        Configuration conf = new SudokuConfiguration("","");

        // se configura la funcion de fitness
        final FitnessFunction funcionFitness = new NumberSituationFitnessFunction(DIMENSION, SUDOKU);
        conf.setFitnessFunction(funcionFitness);

        // se crean los genotipos
        int amountOfNumbersInBoard = SudokuBoard.amountOfNumbersInBoard(DIMENSION);
        final Gene[] sampleGenes = new Gene[amountOfNumbersInBoard];
        for (int i = 0; i < amountOfNumbersInBoard; i++) {
            // se inserta un valor aleatorio en caso de encontrar un cero en la plantilla
            if (SUDOKU[i] == SudokuBoard.NULL_VALUE) {
                sampleGenes[i] = new IntegerGene(conf, 1, DIMENSION * DIMENSION);
            } else {
                sampleGenes[i] = new IntegerGene(conf, SUDOKU[i], SUDOKU[i]);
            }
        }

        // se crea la poblacion de individuos
        final Chromosome sampleChromosome = new Chromosome(conf, sampleGenes);
        conf.setSampleChromosome(sampleChromosome);
        conf.setPopulationSize(POPULATION_SIZE);
        final Genotype population = Genotype.randomInitialGenotype(conf);

        // variables utilizadas durante la evolucion de las generaciones
        double max = 0;
        int generation = 0;
        boolean solved = false;
        SudokuBoard mejorSudoku = null;
        final DecimalFormat df = new DecimalFormat("#0000");
        Texto texto = new Texto();
        while (!solved) {
            // se evoluciona la poblacion
            population.evolve();

            // recuperamos al individuo con mayor fitness
            final IChromosome mejorSolucionHastaAhora = population.getFittestChromosome();
            mejorSudoku = NumberSituationFitnessFunction.createSudokuFromChromosome(DIMENSION, mejorSolucionHastaAhora);

            if (max < funcionFitness.getFitnessValue(mejorSolucionHastaAhora)) {
                max = funcionFitness.getFitnessValue(mejorSolucionHastaAhora);
                texto.addLog("Generacion: " + df.format(generation)+"    fitness: " + (int) funcionFitness.getFitnessValue(mejorSolucionHastaAhora)+"     col: " + mejorSudoku.calculateNonDuplicatedValuesInColumns()+"     fil: " + mejorSudoku.calculateNonDuplicatedValuesInLines()+"     cua: " + mejorSudoku.calculateNonDuplicatedValuesInSquares()+"\n");                
                
                System.out.println("Generacion: " + df.format(generation) +
                        "\tfitness: " + (int) funcionFitness.getFitnessValue(mejorSolucionHastaAhora) +
                        "\tcol: " + mejorSudoku.calculateNonDuplicatedValuesInColumns() +
                        "\tfil: " + mejorSudoku.calculateNonDuplicatedValuesInLines() +
                        "\tcua: " + mejorSudoku.calculateNonDuplicatedValuesInSquares());

                if ((mejorSudoku.calculateNonDuplicatedValuesInSquares() == SudokuBoard.amountOfNumbersInBoard(DIMENSION)) &&
                        (mejorSudoku.calculateNonDuplicatedValuesInColumns() == SudokuBoard.amountOfNumbersInBoard(DIMENSION)) &&
                        (mejorSudoku.calculateNonDuplicatedValuesInLines() == SudokuBoard.amountOfNumbersInBoard(DIMENSION))) {
                    solved = true;
                }
            }
            generation++;
        }
        texto.setResul(mejorSudoku.toString());
        //
        System.out.println(mejorSudoku.toString());
        return texto;
    }

    public static int[] getTEMPLATE_SUDOKU_3X3() {
        return TEMPLATE_SUDOKU_3X3;
    }
    
    /**
     *
     * @return
     */
    public static String getInit(){
        final StringBuilder sb = new StringBuilder();
        int i =1;
        for (int a :TEMPLATE_SUDOKU_3X3 ){
            if(i==1){
                sb.append("  ");
            }
            if(a==0){
                sb.append("_").append("  ");
            }else{
                sb.append(a).append("  ");
            }
            
            
            if(i%9==0 && i>0){
                if(i<80){
                    sb.append("\n  ");
                }
            }
            i++;
        }
        return sb.toString();
    }
    
}
