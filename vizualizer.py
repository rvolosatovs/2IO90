import sys

class InputParser:
	def __init__(self, input):
		self.heightIsFixed = 0
		self.height = 0
		self.numRectangles = 0
		self.positions = []
		self.sizes = []

		self.getPositions(input)
		self.getSizes(input)


	def getPositions(self, lines):
		intStart = lines.find('placement of rectangles') + len('placement of rectangles\n')
		positions = lines[intStart:].split('\n')

		for p in positions:
			self.positions.append(p.split(' '))

	def getSizes(self, lines):
		inputInts = lines[lines.find('number of rectangles:')+len('number of rectangles: '):lines.find('\nplacement of rectangles')].split('\n')
		
		self.numRectangles = inputInts[0]
		
		sizes = []
		for i in inputInts:
			sizes.append(i.split(' '))
		sizes.pop(0)
		self.sizes = sizes


	

print("paste output, control d when done")        
lines = sys.stdin.read()
input = InputParser(lines)

print input.numRectangles
print input.sizes
print input.positions
