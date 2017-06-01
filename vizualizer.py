from __future__ import division
import sys
import matplotlib.pyplot as plt
import matplotlib.patches as patches
import re
import os
from datetime import datetime
from random import randint

#
# Parses input
# @param input
#
class InputParser:
    def __init__(self, input):
        self.height = 0
        self.heightIsFixed = self.getContainerHeight(input)
        self.rotationsAllowed = self.getRotationsAllowed(input)
        self.size, self.numRectangles = self.getSizes(input)
        self.position = self.getPositions(input)

    def getRotationsAllowed(self, input):
        intStart = lines.find('rotations allowed: ') + len('rotations allowed: ')
        subString = lines[intStart:(intStart + 3)]

        if (subString == 'yes'):
            return True
        else:
            return False

    def getContainerHeight(self, lines):
        intStart = lines.find('container height: ') + len('container height: ')
        subString = lines[intStart:(intStart + 5)]

        if (subString == 'fixed'):
            self.height = int(lines[(intStart + 5):intStart + 20].partition('\n')[0])
            return True
        else:
            return False

    def getPositions(self, lines):
        intStart = lines.find('placement of rectangles') + len('placement of rectangles\n')
        positions = lines[intStart:].split('\n')

        parsedPos = []

        for p in positions:
            parsedPos.append(p.split(' '))

        return parsedPos

    def getSizes(self, lines):
        inputInts = lines[lines.find('number of rectangles:') + len('number of rectangles: '):lines.find(
            '\nplacement of rectangles')].split('\n')

        numRectangles = int(inputInts[0])

        sizes = []

        for i in inputInts:
            sizes.append(i.split(' '))

        sizes.pop(0)
        return sizes, numRectangles


#
# builds plot in vizualizer folder
#
class PlotBuilder:
    def __init__(self, input):
        self.folderChecker()
        self.areaOccupied = 0

        fig = plt.figure(figsize=(5, 5))

        ax = fig.add_subplot(111, aspect='equal')
        maxY = 0
        maxX = 0

        colors = ['#002aff', '#294cff', '#5470ff', '#8397ff', '#a6b5ff', '#cad3ff']
        # patterns = ['-', '+', 'x', 'o', 'O', '.', '*']  # more patterns

        if not input.rotationsAllowed:

            for i in range(0, int(input.numRectangles)):

                # keep track of max y for border
                if int(input.position[i][1]) + int(input.size[i][1]) > maxY:
                    maxY = int(input.position[i][1]) + int(input.size[i][1]) + 1
                if int(input.position[i][0]) + int(input.size[i][0]) > maxX:
                    maxX = int(input.position[i][0]) + int(input.size[i][0]) + 1

                ax.add_patch(
                        patches.Rectangle(
                            (int(input.position[i][0]), int(input.position[i][1])),
                            int(input.size[i][0]),
                            int(input.size[i][1]),
                            ls='solid', lw=1,
                            facecolor=colors[randint(0, 5)],
                            edgecolor="black",
                            )
                        )
                self.updateAreaOccupied(int(input.size[i][0]) * int(input.size[i][1]))
        else:
            for i in range(0, int(input.numRectangles)):
                if (input.position[i][0] == "no"):
                    if int(input.position[i][2]) + int(input.size[i][1]) > maxY:
                        maxY = int(input.position[i][2]) + int(input.size[i][1]) + 1
                    if int(input.position[i][1]) + int(input.size[i][0]) > maxX:
                        maxX = int(input.position[i][1]) + int(input.size[i][0]) + 1

                    ax.add_patch(
                            patches.Rectangle(
                                (int(input.position[i][1]), int(input.position[i][2])),
                                int(input.size[i][0]),
                                int(input.size[i][1]),
                                ls='solid', lw=1,
                                facecolor=colors[randint(0, 5)],
                                edgecolor="black",
                                )
                            )

                elif (input.position[i][0] == "yes"):
                    if int(input.position[i][2]) + int(input.size[i][0]) > maxY:
                        maxY = int(input.position[i][2]) + int(input.size[i][0]) + 1
                    if int(input.position[i][1]) + int(input.size[i][1]) > maxX:
                        maxX = int(input.position[i][1]) + int(input.size[i][1]) + 1

                    ax.add_patch(
                            patches.Rectangle(
                                (int(input.position[i][1]), int(input.position[i][2])),
                                int(input.size[i][1]),
                                int(input.size[i][0]),
                                ls='solid', lw=1,
                                facecolor=colors[randint(0, 5)],
                                edgecolor="black",
                                )
                            )
                    self.updateAreaOccupied(int(input.size[i][0]) * int(input.size[i][1]))

        ax.set_ylim([0, maxY])
        ax.set_xlim([0, maxX])

        maxArea = (maxX - 1) * (maxY - 1)
        percentage = self.areaOccupied/maxArea
        print(">" + str(percentage * 100) + "% of area used.")

        plt.show()

    def updateAreaOccupied(self, area):
        self.areaOccupied = self.areaOccupied + area

    def folderChecker(self):
        if not os.path.exists('vizualizer'): os.makedirs('vizualizer')


print("####################################\n#paste output, control d when done#\n####################################")
lines = sys.stdin.read()
input = InputParser(lines)
PlotBuilder(input)
print(">graph plotted in vizualizer folder")
