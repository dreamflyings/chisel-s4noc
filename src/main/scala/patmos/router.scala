/*
 * Authors: Constantina Ioannou
 *
 */
package patmos
import chisel3._
import chisel3.util._
//import Chisel.Node._

import ConstantsN._



class router() extends Module {
  //val io = new routerIO()

  val io = IO(new routerIO())

  val reg_slotCount = Reg(init = UInt(0, TDM_PERIOD))
 
// =====================================================================
// Inistantiate Direction registers 
// =====================================================================

 val init_direction_output = Wire(new routeDir())

  init_direction_output.up:= D
  init_direction_output.right:= D
  init_direction_output.down:= D
  init_direction_output.left:= D
  init_direction_output.local:=D
  init_direction_output.dc:= D
 // io.test:=Bits(0)
  val directions = Reg(init = init_direction_output) 
directions:= io.routeInfo.rInfo
//when (directions.up===Bits(1)){
//	io.test:= Bits(1)
//}

// =====================================================================
// instanciate data output  and input registers 
// =====================================================================


 val init_phit = Wire(new phit())

 init_phit.data := 0.U
 init_phit.valid := 0.U

 val dout_up = Reg (init = init_phit)
 val dout_right = Reg(init = init_phit)
 val dout_down = Reg (init = init_phit)
 val dout_left = Reg (init = init_phit)
 val dout_local = Reg (init = init_phit)


 val din_up = Reg (init = init_phit)
 val din_right = Reg(init = init_phit)
 val din_down = Reg (init = init_phit)
 val din_left = Reg (init = init_phit)
 val din_local = Reg (init = init_phit)


// =====================================================================
// Input register receive input values
// ===================================================================== 

 din_up := io.up_din
 din_right := io.r_din
 din_down := io.dn_din
 din_left := io.le_din
 din_local := io.lc_din
//io.test:=  io.slotCounter_router_io

  io.routeInfo.slotCounter:= reg_slotCount


// =====================================================================
// Slot Counter
// ===================================================================== 

  when(reg_slotCount === UInt(TDM_PERIOD)) {
    reg_slotCount := UInt(0)
  }

  reg_slotCount := reg_slotCount + UInt(1)

// ===================================================================== 

// ===================================================================== 
// OUTPUT PACKET TO (UP/NORTH)
// ===================================================================== 
// NORTH OUTPUT
// ===================================================================== 

	switch (directions.up) {
		is(N){	
			dout_up:= din_up
		}
		is(E){
			dout_up:= din_right

		}
		is(S){
			dout_up:= din_down

		}
		is(W){
			dout_up:= din_left
		}
		is(L){
			dout_up:=din_local
		}
		is(D){
			dout_up:= init_phit
		}
	}

// ===================================================================== 
// EAST OUTPUT
// ===================================================================== 

	switch (directions.right) {
		is(N){	
			dout_right:= din_up
		}
		is(E){
			dout_right:= din_right

		}
		is(S){
			dout_right:= din_down

		}
		is(W){
			dout_right:= din_left
		}
		is(L){
			dout_right:=din_local
		}
		is(D){
			dout_right:= init_phit
		}
	}


// ===================================================================== 
// SOUTH OUTPUT
// ===================================================================== 

	switch (directions.down) {
		is(N){	
			dout_down:= din_up
		}
		is(E){
			dout_down:= din_right

		}
		is(S){
			dout_down:= din_down

		}
		is(W){
			dout_down:= din_left
		}
		is(L){
			dout_down:=din_local
		}
		is(D){
			dout_down:= init_phit
		}
	}


// ===================================================================== 
// WEST OUTPUT
// ===================================================================== 

	switch (directions.left) {
		is(N){	
			dout_left:= din_up
		}
		is(E){
			dout_left:= din_right

		}
		is(S){
			dout_left:= din_down

		}
		is(W){
			dout_left:= din_left
		}
		is(L){
			dout_left:=din_local
		}
		is(D){
			dout_left:= init_phit
		}
	}


// ===================================================================== 
// LOCAL OUTPUT
// ===================================================================== 

	switch (directions.local) {
		is(N){	
			dout_local:= din_up
		}
		is(E){
			dout_local:= din_right

		}
		is(S){
			dout_local:= din_down

		}
		is(W){
			dout_local:= din_left
		}
		is(L){
			dout_local:=din_local
		}
		is(D){
			dout_local:= init_phit
		}
	}



//io.test:= io.rInfo_router_io.up
// =====================================================================  
// testing outputs//

io.up_dout:= dout_up
io.dn_dout:= dout_down
io.le_dout:= dout_left
io.r_dout:= dout_right
io.lc_dout:= dout_local
io.ready := 0.U
io.test := 0.U

}




