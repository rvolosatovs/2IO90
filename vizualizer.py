class Input:
	def __init__(self):
		self.heightIsFixed = 0
		self.height = 0
		self.numRectangles = 0

print("please copy and paste your charge discharge data.\n"
      "To end recording Press Ctrl+d on Linux/Mac on Crtl+z on Windows")
lines = []
        
lines.append(raw_input())

end = lines.find('placement of rectangles') + len('placement of rectangles')

input = Input()
input.find= lines[1]

#print input.heightIsFixed
