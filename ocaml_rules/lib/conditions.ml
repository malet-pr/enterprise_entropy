open Model

(******************* MEETING **********************)

let isMeetingTypeIs (m:meeting)(x:meeting_type):bool = 
  m.meeting_type = x

let isDurationGreaterThan (m:meeting)(x:int):bool = 
  m.duration_min > x

let isMeetingDriftIs (m:meeting)(x:meeting_drift):bool =
  m.drift = x

let isDeepDiveIs (m:meeting)(x: bool):bool =
  m.deep_dive = x

let isDurationAtLeast (m:meeting)(x:int):bool = 
  m.duration_min >= x

let isDurationAtMost (m:meeting)(x:int):bool = 
  m.duration_min <= x

(******************** ISSUE ***********************)

let isIssuePriorityIs (i:issue)(x:issue_priority):bool = 
  i.priority = x

let isIssuePriorityIn (i:issue)(xs:issue_priority list):bool = 
  List.exists (fun x -> i.priority = x) xs

let isIssueUnderstoodOnlyBy(i: issue) (u: understanding): bool =   
  i.understood_by = [u]

let isIssueUnderstoodByList (i: issue) (ul: understanding list): bool = 
  i.understood_by = ul

let isIssueStageIs (i: issue)(s: stage) : bool =
  i.status.stage = s  

let isIssueRiskIs (i: issue)(r: risk) : bool =
  i.status.risk = Some r


(***************** PARTICIPANTS *******************)

let isExistsInterestedParticipantWithRole (ps: participant list)(rl: role list): bool = 
  List.exists (fun p -> List.mem p.role rl && p.interested) ps

let isNotExistsInterestedParticipantWithRole (ps: participant list)(rl: role list): bool = 
  not (isExistsInterestedParticipantWithRole ps rl)   

let isExistsUnderstandingParticipantWithRole (ps: participant list)(rl: role list): bool = 
  List.exists (fun p -> List.mem p.role rl && p.understands) ps

let isNotExistsUnderstandingParticipantWithRole (ps: participant list)(rl: role list): bool = 
  not (isExistsUnderstandingParticipantWithRole ps rl)

let isAllParticipantsUnderstand (ps: participant list) : bool =
  List.exists (fun p -> p.understands)ps

let isNoParticipantUnderstands (ps: participant list) : bool =
  not (isAllParticipantsUnderstand ps)

let isUnderstandsCountAtLeast (ps: participant list) (c: int) : bool =
  (List.fold_left (fun acc p -> acc + Bool.to_int p.understands) 0 ps) >= c

let isUnderstandsCountAtMost (ps: participant list) (c: int) : bool =
  (List.fold_left (fun acc p -> acc + Bool.to_int p.understands) 0 ps) <= c  

let isParticipantCountAtLeast (ps: participant list) (c: int) : bool =
  List.length ps >= c

let isParticipantCountAtMost (ps: participant list) (c: int) : bool =
  List.length ps <= c

let isExistsParticipantWithRole (ps: participant list)(rl: role list): bool = 
  List.exists (fun p -> List.mem p.role rl) ps

let isNotExistsParticipantWithRole (ps: participant list)(rl: role list): bool = 
  not (isExistsParticipantWithRole ps rl)

  