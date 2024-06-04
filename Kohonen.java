/*
*   Copyright: Denny Hermawanto - 2006
*   Mail: d_3_nny@yahoo.com
*/

public class Kohonen{

	private void DefineInput(){
		inputvector = new double[numberofinput][inputdimension];
		
		inputvector[0][0] = 1;
		inputvector[0][1] = 1;
		inputvector[0][2] = 0;
		inputvector[0][3] = 0;

		inputvector[1][0] = 0;
		inputvector[1][1] = 0;
		inputvector[1][2] = 0;
		inputvector[1][3] = 1;

		inputvector[2][0] = 0;
		inputvector[2][1] = 0;
		inputvector[2][2] = 1;
		inputvector[2][3] = 0;

		inputvector[3][0] = 1;
		inputvector[3][1] = 1;
		inputvector[3][2] = 0;
		inputvector[3][3] = 0;
	}
	
	private double RandomNumberGenerator(){
		java.util.Random rnd = new java.util.Random();
		return rnd.nextDouble();
	}
	
	private double LearningRateDecay(double currentlearningrate){
		double result = 0;
		result = 0.8 * currentlearningrate;
		return result;
	}
	
	private void InitializeWeigths(){
		weights = new double[numberofcluster][inputdimension];
		for(int i=0;i<numberofcluster;i++){
			for(int j=0;j<inputdimension;j++){
				weights[i][j] = RandomNumberGenerator();
			}
		}
	}
	
	private double ComputeEuclideanDistance(double[] vector1, double[] vector2){
		double result;
		double distance =0;
		for(int j=0;j<inputdimension;j++){
			distance += Math.pow((vector1[j] - vector2[j]), 2);
		}
		result = distance;
		return result;
	}
	
	private void TrainKohonen(int maxiteration){
		euclideandistance = new double[numberofcluster];

		for(int iter=0;iter<maxiteration;iter++){
			for(int k=0;k<numberofinput;k++){
				//Get the winning neuron
				winningneuron = 0;
				for(int i=0;i<numberofcluster;i++){
					euclideandistance[i] = ComputeEuclideanDistance(weights[i],inputvector[k]);
					if(i!=0){
						if(euclideandistance[i]<euclideandistance[winningneuron]){
							winningneuron = i;
						}
					}
					//System.out.println(euclideandistance[i]);
				}
				//System.out.println("Winner:"+winningneuron);
				//Update the winning neuron
				for(int i=0;i<inputdimension;i++){
					weights[winningneuron][i] += learnrate * (inputvector[k][i] - weights[winningneuron][i]);
				}
			}
			learnrate = LearningRateDecay(learnrate);	
			System.out.println("Learn Rate:"+learnrate);
		}
	}
	
	private void MappingInputVector(){
		for(int k=0;k<numberofinput;k++){
			winningneuron = 0;
			for(int i=0;i<numberofcluster;i++){
				euclideandistance[i] = ComputeEuclideanDistance(weights[i],inputvector[k]);
				if(i!=0){
					if(euclideandistance[i]<euclideandistance[winningneuron]){
						winningneuron = i;
					}
				}
			//System.out.println(euclideandistance[i]);
			}
			System.out.println("Input["+k+"] -> Cluster No:"+winningneuron);
		}
	}
	
	public void RunKohonen(){
		DefineInput();
		InitializeWeigths();
		TrainKohonen(50);
		MappingInputVector();
	}
	
	public static void main(String[] args){
		Kohonen kohonen = new Kohonen();
		kohonen.numberofcluster = 2;
		kohonen.inputdimension = 4;
		kohonen.numberofinput = 4;
		kohonen.learnrate = 0.6;
		kohonen.RunKohonen();
	}
	
	//define variables
	private double[][] inputvector;
	private double[][] weights;
	private double[] euclideandistance;
	private int numberofcluster;
	private int inputdimension;
	private int numberofinput;
	private double learnrate;
	private int winningneuron;
}