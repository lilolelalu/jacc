TITLE MASM Template						(main.asm)


INCLUDE Irvine32.inc
swap PROTO,			; swap prototype
	ptrArr:PTR DWORD,
	a:DWORD,
	b:DWORD

quicksort PROTO,		; quicksort prototype
	ptrArr:PTR DWORD,
	beginNum:DWORD,
	endNum:DWORD

.data
arr	DWORD 9h,1h,2h,8h,7h,6h,4h,5h,3h	;define the content of the array

.code

main PROC
	INVOKE	quicksort,addr arr,0,8
	mov	eax,arr
	mov	ebx,arr+4
	mov	ecx,arr+8
	mov	edx,arr+12
	mov	esi,arr+16
	mov	edi,arr+20
	call DumpRegs
	mov	eax,arr+24
	mov	ebx,arr+28
	mov  ecx,arr+32
	call	DumpRegs
	exit
main ENDP

swap PROC USES eax ebx ecx esi edi,
	ptrArr:PTR DWORD,
	a:DWORD,
	b:DWORD
	mov	eax,4h		;elements in array is DWORD, so a DWORD is 4 Bytes
	mov	ebx,a
	mul	ebx
	mov	ebx,eax
	mov	eax,4h
	mov	ecx,b
	mul	ecx
	mov	ecx,eax
	add	ebx,ptrArr
	add	ecx,ptrArr
	mov	esi,ebx
	mov	edi,ecx
	mov	eax,[esi]		; get first integer
	xchg	eax,[edi]		; exchange with second
	mov	[esi],eax		; replace first integer
	ret
swap ENDP

quicksort	PROC USES esi eax ebx,
	ptrArr:PTR DWORD,
	beginNum:DWORD,
	endNum:DWORD
	LOCAL midNum:DWORD,midPos:DWORD,count:DWORD
	mov	ebx,endNum		
	cmp	beginNum,ebx
	jge	return
	mov	ebx,beginNum
	mov	eax,4h			;DWORD = 4Bytes
	mul	ebx
	add	eax,ptrArr
	mov	esi,eax
	mov	eax,[esi]
	mov	midNum,eax
	mov	eax,beginNum
	mov	midPos,eax
	add	eax,1h
	mov	count,eax
body:					;for loop body
	mov	ebx,endNum
	cmp	count,ebx
	ja	outloop
	mov	ebx,count
	mov	eax,4h			;DWORD = 4Bytes
	mul	ebx
	add	eax,ptrArr
	mov	esi,eax
	mov	eax,[esi]
	cmp	eax,midNum
	jge	leaveout			;if(a[i] < mid_num)?
	inc	midPos
	INVOKE	swap,ptrArr,count,midPos		;swap(a[midPos + 1], a[i])
leaveout:					;if(a[i] < mid_num)then
	inc	count
	jmp	body
outloop:
	INVOKE	swap,ptrArr,midPos,beginNum	;swap(a[midPos], a[beginNum])
	mov	eax,midPos
	dec	eax
	INVOKE	quicksort,ptrArr,beginNum,eax	;quicksort(arr[], beginNum, midPos - 1)
	add	eax,2h
	INVOKE	quicksort,ptrArr,eax,endNum	;quicksort(arr[], midPos + 1, endNum)

return:
	ret
quicksort ENDP
END main


	