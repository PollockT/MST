/* Author: Theodore Pollock
   File Name: Main.java
   Contact: pollock@nsuok.edu // PollockT@TulsaPreTech.com
   Date: 12/7/19
   File contains the creation of a graph that starts from A, and calculates the MST and outputs it.
 */
package com.tulsapretech.algorithims;

import java.util.logging.Logger;
import java.util.Arrays;

public class Main{

    int vertices, edges;    // V-> no. of vertices & E->no.of edges NOT INITIALIZED FOR REASON!!!!!!
    Edge[] edge; // collection of all edges

    public static void main (String[] args){

        int vertices = 6; // Number of vertices in the Given Graph
        int edges = 6; // Number of edges in the Given Graph
        Main graph1 = new Main(vertices, edges);

        // adding edge 0-1 i.e A-B
        graph1.edge[0].src = 0;
        graph1.edge[0].dest = 1;
        graph1.edge[0].weight = 4;
        graph1.edge[0].vertices = "A to B";

        System.out.print(graph1.edge[0].vertices);
        System.out.println(" with a cost of " + graph1.edge[0].weight);

        // adding edge 0-2 i.e A-C
        graph1.edge[1].src = 0;
        graph1.edge[1].dest = 2;
        graph1.edge[1].weight = 2;
        graph1.edge[1].vertices = "A to C";

        System.out.print(graph1.edge[1].vertices);
        System.out.println(" with a cost of " + graph1.edge[1].weight);

        // adding edge 1-2 i.e B-C
        graph1.edge[2].src = 1;
        graph1.edge[2].dest = 2;
        graph1.edge[2].weight = 1;
        graph1.edge[2].vertices = "B to C";
        System.out.print(graph1.edge[2].vertices);
        System.out.println(" with a cost of " + graph1.edge[2].weight);

        // adding edge 1-3 i.e B-D
        graph1.edge[3].src = 1;
        graph1.edge[3].dest = 3;
        graph1.edge[3].weight = 1;
        graph1.edge[3].vertices = "B to D";
        System.out.print(graph1.edge[3].vertices);
        System.out.println(" with a cost of " + graph1.edge[3].weight);

        // adding edge 1-4 i.e B-E
        graph1.edge[4].src = 1;
        graph1.edge[4].dest = 4;
        graph1.edge[4].weight = 2;
        graph1.edge[4].vertices = "B to E";
        System.out.print(graph1.edge[4].vertices);
        System.out.println(" with a cost of " + graph1.edge[4].weight);

        // adding edge 3-5 i.e D-F
        graph1.edge[5].src = 3;
        graph1.edge[5].dest = 5;
        graph1.edge[5].weight = 5;
        graph1.edge[5].vertices = "D to F";
        System.out.print(graph1.edge[5].vertices);
        System.out.println(" with a cost of " + graph1.edge[5].weight);


        System.out.println("\n\n\n");
        graph1.MST();
    }

    // Creates a Main with V vertices and E edges
    public Main(int vertices, int edges){
        this.vertices = vertices;
        this.edges = edges;
        edge = new Edge[edges];
        for (int i = 0; i < vertices; ++i)
            edge[i] = new Edge();
    }

    // Implementing class to represent a Main edge
    public static class Edge implements Comparable<Edge>{
        int src, dest, weight;
        String vertices;

        // Comparator function is used for sorting edges using their weight
        public int compareTo(Edge compareEdge){
            return this.weight-compareEdge.weight;
        }
    }

    // Implementing class to represent a subset for union-locate
    public static class subset{
        int parentNode, rankNode;
    }


    // A utility function to locate set of an element i
    public int locate( subset[] subsets, int i){
        // locate root and make root as parent_node of i,this is nothing but path compression
        if (subsets[i].parentNode != i)
            subsets[i].parentNode = locate(subsets, subsets[i].parentNode);

        return subsets[i].parentNode;
    }

    // A function that does union of two sets of x and y
    public void union(subset[] subsets, int x, int y){
        int xRoot = locate(subsets, x);
        int yRoot = locate(subsets, y);

        // Attach smaller rank tree under root of high rank tree(union by rank)
        if (subsets[xRoot].rankNode < subsets[yRoot].rankNode){
            subsets[xRoot].parentNode = yRoot;
        }
        else if (subsets[xRoot].rankNode > subsets[yRoot].rankNode){
            subsets[yRoot].parentNode = xRoot;
        }
            // If ranks are same, then make one as root and increment its rank by one
        else{
            subsets[yRoot].parentNode = xRoot;
            subsets[xRoot].rankNode++;
        }
    }

    // construction of Minimum spanning Tree
    public void MST(){
        Edge[] result = new Edge[vertices]; // To store the resultant MST
        int i1 = 0; //index variable which used for result[]
        int i2; //index variable which used for sorted edges
        for (i2 = 0; i2 < vertices; ++i2)
            result[i2] = new Edge();

        // Step 1: Sort all the edges in non-decreasing order of their
        // weight. If we are not allowed to change the given Main, we
        // can create a copy of array of edges
        Arrays.sort(edge);

        // Allocate memory for creating vertices subsets
        subset[] subsets = new subset[vertices];
        for(i2=0; i2 < vertices; ++i2)
            subsets[i2]= new subset();

        // Create vertices subsets with single elements
        // int j is used for loop indexing
        for (int i3 = 0; i3 < vertices; ++i3){
            subsets[i3].parentNode = i3;
            subsets[i3].rankNode = 0;
        }

        i2 = 0; // Index used to pick successive edge

        // Number of edges to be taken is equal to 'vertices -1'
        while (i1 < vertices - 1){
            // Step 2: Pick the smallest edge. And increment the index for next iteration
            new Edge();
            Edge next_edge;
            next_edge = edge[i2++];

            int x = locate(subsets, next_edge.src);
            int y = locate(subsets, next_edge.dest);

            // If by including this edge does't cause to form cycle,include it in result and increment the index of
            // the results for the next edge
            if (x != y){
                result[i1++] = next_edge;
                union(subsets, x, y);
            }
        }
        System.out.println("Following are the edges in " + "the constructed MST");
        for (i2 = 0; i2 < i1; ++i2){
            System.out.println("Link " + result[i2].src +" to Link " + result[i2].dest+ " == " + result[i2].weight +
                    " is the cost of the movement");}
    }
}